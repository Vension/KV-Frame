package com.vension.customview.radarscanview.scan_1;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.vension.customview.R;


/**
 * Created by Andy on 2016/3/17.
 */
public class RippleView extends AppCompatImageView {

    /*波纹颜色*/
    private int mRippleColor;
    /*波纹个数*/
    private int mRippleCount;
    /*图片的大小等级，值越大图片越小*/
    private int mDrawableSizeLevel;
    /*是否显示动画*/
    private boolean mHasAnimator;

    private Paint mPaint;

    /*触发动画的参数*/
    private int currentProgress;
    /*动画标志*/
    private boolean animatorIsRunning;
    private ObjectAnimator mAnimator;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RippleView);

        mRippleColor = array.getColor(R.styleable.RippleView_circleColor,
                ContextCompat.getColor(context, R.color.material_blue_200));
        mRippleCount = array.getInt(R.styleable.RippleView_circleCount, 5);
        mDrawableSizeLevel = array.getInt(R.styleable.RippleView_drawableSizeLevel, 2);
        mHasAnimator = array.getBoolean(R.styleable.RippleView_hasAnimator, true);
        array.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mRippleColor);

        mAnimator = ObjectAnimator.ofInt(this, "currentProgress", 0, 100);
        mAnimator.setDuration(3000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mAnimator.setRepeatMode(ObjectAnimator.RESTART);
        if (mHasAnimator) {
            start();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null == drawable) {
            return;
        }
        /*中心点*/
        int center = getMeasuredWidth() / 2;
        /*根据等级参数得到drawable的大小*/
        int drawableSize = getMeasuredWidth() / mDrawableSizeLevel;
        /*drawable调整后的半径*/
        int drawableRadius = drawableSize / 2;
        /*drawable在父容器中的位置*/
        int drawableLT = center - drawableRadius;
        int drawableRB = center + drawableRadius;

        /*重新调整drawable的大小*/
        drawable.setBounds(drawableLT, drawableLT, drawableRB, drawableRB);

        /*drawable占用后剩下的部分*/
        int leave = center - drawableRadius;
        /*水纹波平均能占用的单位大小*/
        int perSize = leave / mRippleCount;
        /*画水纹波*/
        for (int i = 0; 0 != leave && i < mRippleCount; i++) {
            int progress = (i * perSize + currentProgress * leave / 100) % leave;
            int alpha = 255 - 255 * progress / leave;
            mPaint.setAlpha(alpha);
            canvas.drawCircle(center, center, drawableRadius + progress, mPaint);
            /*int progress = (currentProgress + i * 100 / (mRippleCount)) % 100;
            mPaint.setAlpha(255 - 255 * progress / 100);
            canvas.drawCircle(center, center, center * progress / 100, mPaint);*/
        }
        drawable.draw(canvas);
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        this.invalidate();
    }

    public boolean isAnimatorIsRunning() {
        return animatorIsRunning;
    }

    public void start() {
        if (!animatorIsRunning) {
            mAnimator.start();
            animatorIsRunning = true;
        }
    }

    public void stop() {
        if (animatorIsRunning) {
            mAnimator.end();
            currentProgress = 0;
            invalidate();
            animatorIsRunning = false;
        }
    }
}
