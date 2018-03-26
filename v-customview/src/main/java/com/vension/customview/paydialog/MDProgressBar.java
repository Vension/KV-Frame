package com.vension.customview.paydialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import com.vension.customview.R;


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/21 11:07
 * 描  述：自定义圆形加载进度条
 * ========================================================
 */

public class MDProgressBar extends View {

	private final static  String TAG = MDProgressBar.class.getSimpleName();

	private static final float DEFAULT_MAX_ANGLE = -305f; //最大角度
	private static final float DEFAULT_MIN_ANGLE = -19f; //最小角度

	private static final int DEFAULT_DURATION = 660;//默认的动画时间

	private static final int DEFAULT_ARC_COLOR = Color.BLUE;
	private int arcColor = DEFAULT_ARC_COLOR; //圆弧颜色
	private float mBorderWidth; //圆弧外边框宽度
	private float mRadius; //圆形进度条的半径

	private Paint mPaint; //画笔
	private RectF arcRectF; //矩阵

	private float startAngle = -45f;//圆弧开始角度
	private float sweepAngle = -19f;//圆弧清除角度
	private float incrementAngele = 0;//增量角度

	private AnimatorSet animatorSet;
	private TickAnimation mTickAnimation;
	private boolean isAnimationOverHalf = false;//判断"对勾"动画是否过半,"对勾"由两条线绘制而成。
    private float startX1;
    private float startY1;
    private float stopX1;
    private float stopY1;
    private float stopX2;
    private float stopY2;

	//是否需要开始绘制对勾
	private boolean isNeedTick = false;
	private int mResize;

    private OnPasswordCorrectlyListener mListener;

	public MDProgressBar(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}

	public MDProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context,attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.materialStatusProgressAttr);
		arcColor = typedArray.getColor(R.styleable.materialStatusProgressAttr_arcColor,Color.parseColor("#4a90e2"));
		mBorderWidth = typedArray.getDimension(R.styleable.materialStatusProgressAttr_progressBarBorderWidth,getResources().getDimension(R.dimen.material_status_progress_border));
	    typedArray.recycle();

		mPaint = new Paint();
		mPaint.setColor(arcColor);//画笔颜色
		mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setAntiAlias(true); //开启所画的边缘抗锯齿平滑
        mPaint.setStyle(Paint.Style.STROKE);

		arcRectF = new RectF();
        mTickAnimation = new TickAnimation();
		mTickAnimation.setDuration(800);
		//对勾动画监听
        mTickAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//当对勾动画完成后,延迟一秒回掉,不然动画效果不明显
				if (mListener != null){
					postDelayed(new Runnable() {
						@Override
						public void run() {
							mListener.onPasswordCorrectly();
						}
					},1000);
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		startY1 = getMeasuredHeight() / 2;
		mRadius = getMeasuredHeight() / 2 - 2 * mBorderWidth;
		startX1 = startY1 - getMeasuredHeight() / 5;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		arcPaint();
		//画圆弧
		canvas.drawArc(arcRectF,startAngle + incrementAngele,sweepAngle,false,mPaint);
		if (animatorSet == null || !animatorSet.isRunning() && !isNeedTick){
			startAnimatorSet();
		}
		if (isNeedTick){
			//补全圆
			arcPaint();
			canvas.drawArc(arcRectF,startAngle + incrementAngele + sweepAngle,360 - sweepAngle,false,mPaint);
			linePaint();
			//画第一根线
			canvas.drawLine(startX1,startY1,stopX1,stopY1,mPaint);
			if (isAnimationOverHalf){
				// -2 +2 是为了两根线尽可能靠拢
				canvas.drawLine(stopX1 - 2,stopY1 + 2,stopX2,stopY2,mPaint);
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mResize = (w < h) ? w : h;
		setBound();
	}

	@Override
	public void setVisibility(int visibility) {
		switch (visibility){
			case View.VISIBLE:
				startAnimatorSet();
				break;
			case View.INVISIBLE:
				cancelAnimator();
				break;
			case View.GONE:
				cancelAnimator();
				break;

				default:
					break;
		}
		super.setVisibility(visibility);
	}


	private void setBound() {
       int paddingLeft = getPaddingLeft();
       int paddingTop = getPaddingTop();
       arcRectF.set(paddingLeft + mBorderWidth,paddingTop + mBorderWidth,mResize - paddingLeft - mBorderWidth,mResize - paddingTop - mBorderWidth);
	}

	private void arcPaint() {
		mPaint.reset();
		mPaint.setColor(arcColor);
		mPaint.setStrokeWidth(mBorderWidth);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
	}

	private void linePaint() {
		mPaint.reset();
		mPaint.setColor(arcColor);
		mPaint.setStrokeWidth(mBorderWidth);
		mPaint.setAntiAlias(true);
	}

	/**取消动画*/
	private void cancelAnimator() {
		if (animatorSet != null){
			animatorSet.cancel();
			isNeedTick = true;
		}
	}

	/**开启动画*/
	private void startAnimatorSet() {
         isNeedTick = false;
         if (animatorSet != null && animatorSet.isRunning()){
         	animatorSet.cancel();
		 }

		 if (animatorSet == null){
         	animatorSet = new AnimatorSet();
		 }
		 AnimatorSet set = loopAnimatorSet();
		 animatorSet.play(set);
		 animatorSet.addListener(new Animator.AnimatorListener() {

		 	private boolean isCancel = false;

			 @Override
			 public void onAnimationStart(Animator animator) {

			 }

			 @Override
			 public void onAnimationEnd(Animator animator) {
				 if (!isCancel){
					 startAnimatorSet();
				 }
			 }

			 @Override
			 public void onAnimationCancel(Animator animator) {
                     isCancel = true;
			 }

			 @Override
			 public void onAnimationRepeat(Animator animator) {

			 }
		 });
		 animatorSet.start();
	}

	/**
	 *  进度条旋转的动画
	 */
	private AnimatorSet loopAnimatorSet() {
		//从小圈到大圈
		ValueAnimator holdAnimator1 = ValueAnimator.ofFloat(incrementAngele + DEFAULT_MIN_ANGLE,incrementAngele + 115f);
		holdAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				incrementAngele = (float) valueAnimator.getAnimatedValue();
			}
		});
		holdAnimator1.setDuration(DEFAULT_DURATION);
		holdAnimator1.setInterpolator(new LinearInterpolator());

		ValueAnimator expandAnimator = ValueAnimator.ofFloat(DEFAULT_MIN_ANGLE,DEFAULT_MAX_ANGLE);
		expandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				sweepAngle = (float) valueAnimator.getAnimatedValue();
				incrementAngele -= sweepAngle;
				invalidate();
			}
		});
		expandAnimator.setDuration(DEFAULT_DURATION);
		expandAnimator.setInterpolator(new DecelerateInterpolator(2));

		//从大圈到小圈
		ValueAnimator holdAnimator = ValueAnimator.ofFloat(startAngle,startAngle + 115f);
		holdAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				startAngle = (float) valueAnimator.getAnimatedValue();
			}
		});
		holdAnimator.setDuration(DEFAULT_DURATION);
		holdAnimator.setInterpolator(new LinearInterpolator());

		ValueAnimator narrowAnimator = ValueAnimator.ofFloat(DEFAULT_MAX_ANGLE,DEFAULT_MIN_ANGLE);
		narrowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				sweepAngle = (float) valueAnimator.getAnimatedValue();
				invalidate();
			}
		});
		narrowAnimator.setDuration(DEFAULT_DURATION);
		narrowAnimator.setInterpolator(new DecelerateInterpolator(2));

		AnimatorSet mAnimatorSet = new AnimatorSet();
		mAnimatorSet.play(holdAnimator1).with(expandAnimator);
		mAnimatorSet.play(holdAnimator).with(narrowAnimator).after(holdAnimator1);
		return mAnimatorSet;
	}

	public void setSuccessfullyStatus(){
		if (animatorSet != null){
			animatorSet.cancel();
			isNeedTick = true;
			startAnimation(mTickAnimation);
		}
	}


	public void setBorderWidth(int width){
		this.mBorderWidth = width;
	}

	public void setArcColor(int color){
	   this.arcColor = color;
	}

	/** 对勾动画完成回调*/
	public void setOnPasswordCorrectlyListener(OnPasswordCorrectlyListener listener){
		this.mListener = listener;
	}

	public interface OnPasswordCorrectlyListener{
		void onPasswordCorrectly();
	}

	//对勾动画
	private class TickAnimation extends Animation {

		@Override
		protected void applyTransformation(final float interpolatedTime, Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime <= 0.5f) {
				stopX1 = startX1 + mRadius / 3 * interpolatedTime * 2;
				stopY1 = startY1 + mRadius / 3 * interpolatedTime * 2;
				isAnimationOverHalf = false;
			} else {
				stopX2 = stopX1 + (mRadius - 20) * (interpolatedTime - 0.5f) * 2;
				stopY2 = stopY1 - (mRadius - 20) * (interpolatedTime - 0.5f) * 2;
				isAnimationOverHalf = true;
			}
			invalidate();
		}
	}


}


