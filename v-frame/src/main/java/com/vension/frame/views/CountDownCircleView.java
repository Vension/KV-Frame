package com.vension.frame.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.vension.frame.R;


/**
 * @author vension create on 2017/5/23
 * 自定义圆形倒计时
 */
public class CountDownCircleView extends View {

    //定义一些常量(大小写字母切换快捷键 Ctrl + Shift + U)
    private static final int DEFAULT_CIRCLE_SOLIDE_COLOR = Color.parseColor("#2cc6ba");
    private static final int DEFAULT_CIRCLE_STROKE_COLOR = Color.parseColor("#00000000");//大圆边框默认透明色
    private static final int DEFAULT_CIRCLE_STROKE_WIDTH = 2;
    private static final int DEFAULT_CIRCLE_RADIUS = 22;

    private static final int PROGRESS_COLOR = Color.parseColor("#F76E6B");
    private static final int PROGRESS_WIDTH = 2;

    private static final int SMALL_CIRCLE_SOLIDE_COLOR = Color.parseColor("#FFFFFF");
    private static final int SMALL_CIRCLE_STROKE_COLOR = Color.parseColor("#F76E6B");
    private static final int SMALL_CIRCLE_STROKE_WIDTH = 1;
    private static final int SMALL_CIRCLE_RADIUS = 3;

    private static final int TEXT_COLOR = Color.parseColor("#F76E6B");
    private static final int TEXT_SIZE = 13;


    //默认圆
    private int defaultCircleSolideColor = DEFAULT_CIRCLE_SOLIDE_COLOR;
    private int defaultCircleStrokeColor = DEFAULT_CIRCLE_STROKE_COLOR;
    private int defaultCircleStrokeWidth = dp2px(DEFAULT_CIRCLE_STROKE_WIDTH);
    private int defaultCircleRadius = dp2px(DEFAULT_CIRCLE_RADIUS);//半径
    //进度条
    private int progressColor = PROGRESS_COLOR;
    private int progressWidth = dp2px(PROGRESS_WIDTH);
    //默认圆边框上面的小圆
    private int smallCircleSolideColor = SMALL_CIRCLE_SOLIDE_COLOR;
    private int smallCircleStrokeColor = SMALL_CIRCLE_STROKE_COLOR;
    private int smallCircleStrokeWidth = dp2px(SMALL_CIRCLE_STROKE_WIDTH);
    private int smallCircleRadius = dp2px(SMALL_CIRCLE_RADIUS);
    //最里层的文字
    private int textColor = TEXT_COLOR;
    private int textSize = sp2px(TEXT_SIZE);


    //画笔
    private Paint defaultCriclePaint;
    private Paint progressPaint;
    private Paint smallCirclePaint;//画小圆边框的画笔
    private Paint textPaint;
    private Paint defaultCricleSolidePaint;//画内部圆的实心的画笔
    private Paint smallCircleSolidePaint;//画小圆的实心的画笔

    //圆弧开始的角度
    private float mStartSweepValue = -90;
    //当前的角度
    private float currentAngle;
    //提供一个外界可以设置的倒计时数值，毫秒值
    private long countdownTime;
    //中间文字描述
    private String textDesc= "跳过";
    //小圆运动路径Path
    private Path mPath;

    //额外距离
    private float extraDistance = 0.7F;
    private CountDownTimer _CountDownTimer;//倒计时

    public CountDownCircleView(Context context) {
        this(context,null);
    }

    public CountDownCircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountDownCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        initTypedArray(attrs);
        //设置画笔
        setPaint();
    }

    private void initTypedArray(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownCircleView);
        defaultCircleSolideColor = typedArray.getColor(R.styleable.CountDownCircleView_default_circle_solide_color, defaultCircleSolideColor);
        defaultCircleStrokeColor = typedArray.getColor(R.styleable.CountDownCircleView_default_circle_stroke_color, defaultCircleStrokeColor);
        defaultCircleStrokeWidth = (int) typedArray.getDimension(R.styleable.CountDownCircleView_default_circle_stroke_width, defaultCircleStrokeWidth);
        defaultCircleRadius = (int) typedArray.getDimension(R.styleable.CountDownCircleView_default_circle_radius, defaultCircleRadius);
        progressColor = typedArray.getColor(R.styleable.CountDownCircleView_progress_color, progressColor);
        progressWidth = (int) typedArray.getDimension(R.styleable.CountDownCircleView_progress_width, progressWidth);
        smallCircleSolideColor = typedArray.getColor(R.styleable.CountDownCircleView_small_circle_solide_color, smallCircleSolideColor);
        smallCircleStrokeColor = typedArray.getColor(R.styleable.CountDownCircleView_small_circle_stroke_color, smallCircleStrokeColor);
        smallCircleStrokeWidth = (int) typedArray.getDimension(R.styleable.CountDownCircleView_small_circle_stroke_width, smallCircleStrokeWidth);
        smallCircleRadius = (int) typedArray.getDimension(R.styleable.CountDownCircleView_small_circle_radius, smallCircleRadius);
        textColor = typedArray.getColor(R.styleable.CountDownCircleView_text_color, textColor);
        textSize = (int) typedArray.getDimension(R.styleable.CountDownCircleView_text_size, textSize);
        //回收typedArray对象
        typedArray.recycle();
    }

    private void setPaint() {
        //默认圆
        defaultCriclePaint = new Paint();
        defaultCriclePaint.setAntiAlias(true);//抗锯齿
        defaultCriclePaint.setDither(true);//防抖动
        defaultCriclePaint.setStyle(Paint.Style.STROKE);
        defaultCriclePaint.setStrokeWidth(defaultCircleStrokeWidth);
        defaultCriclePaint.setColor(defaultCircleStrokeColor);//这里先画边框的颜色，后续再添加画笔画实心的颜色
        //默认圆的实心画笔
        defaultCricleSolidePaint = new Paint();
        defaultCricleSolidePaint.setAntiAlias(true);
        defaultCricleSolidePaint.setDither(true);
        defaultCricleSolidePaint.setStyle(Paint.Style.FILL);
        defaultCricleSolidePaint.setColor(defaultCircleSolideColor);
        //默认圆上面的进度弧度
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setDither(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔笔刷样式
        //进度上面的小圆
        smallCirclePaint = new Paint();
        smallCirclePaint.setAntiAlias(true);
        smallCirclePaint.setDither(true);
        smallCirclePaint.setStyle(Paint.Style.STROKE);
        smallCirclePaint.setStrokeWidth(smallCircleStrokeWidth);
        smallCirclePaint.setColor(smallCircleStrokeColor);
        //画进度上面的小圆的实心画笔（主要是将小圆的实心颜色设置成白色）
        smallCircleSolidePaint = new Paint();
        smallCircleSolidePaint.setAntiAlias(true);
        smallCircleSolidePaint.setDither(true);
        smallCircleSolidePaint.setStyle(Paint.Style.FILL);
        smallCircleSolidePaint.setColor(smallCircleSolideColor);

        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }

    /**
     * 如果该View布局的宽高开发者没有精确的告诉，则需要进行测量，如果给出了精确的宽高则我们就不管了
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize;
        int heightSize;
        int strokeWidth = Math.max(defaultCircleStrokeWidth, progressWidth);
        if(widthMode != MeasureSpec.EXACTLY){
            widthSize = getPaddingLeft() + defaultCircleRadius*2 + strokeWidth + getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if(heightMode != MeasureSpec.EXACTLY){
            heightSize = getPaddingTop() + defaultCircleRadius*2 + strokeWidth + getPaddingBottom();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        //画默认圆
        canvas.drawCircle(defaultCircleRadius,defaultCircleRadius,defaultCircleRadius,defaultCriclePaint);
        //画默认圆的实心
        canvas.drawCircle(defaultCircleRadius,defaultCircleRadius,defaultCircleRadius - defaultCircleStrokeWidth,defaultCricleSolidePaint);

        //画进度圆弧
        //currentAngle = getProgress()*1.0f/getMax()*360;
        canvas.drawArc(new RectF(0,0,defaultCircleRadius*2,defaultCircleRadius*2),mStartSweepValue, 360*currentAngle,false,progressPaint);
        //画中间文字
        //   String text = getProgress()+"%";
        //获取文字的长度的方法
        float textWidth = textPaint.measureText(textDesc);
        float textHeight = (textPaint.descent() + textPaint.ascent()) / 2;
        canvas.drawText(textDesc, defaultCircleRadius-textWidth/2, defaultCircleRadius-textHeight, textPaint);

        //画小圆
        float currentDegreeFlag = 360*currentAngle + extraDistance;
        float smallCircleX = 0,smallCircleY = 0;
        float hudu = (float) Math.abs(Math.PI * currentDegreeFlag / 180);//Math.abs：绝对值 ，Math.PI：表示π ， 弧度 = 度*π / 180
        smallCircleX = (float) Math.abs(Math.sin(hudu) * defaultCircleRadius + defaultCircleRadius);
        smallCircleY = (float) Math.abs(defaultCircleRadius - Math.cos(hudu) * defaultCircleRadius);
        canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius, smallCirclePaint);
        //画小圆的实心
        canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius - smallCircleStrokeWidth, smallCircleSolidePaint);

        canvas.restore();

    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    //提供一个外界可以设置的倒计时数值
    public void setCountdownTime(long countdownTime){
        this.countdownTime = countdownTime;
//        textDesc = countdownTime / 1000 + "″";
    }
    //属性动画
    public void startCountDownTime(final OnCountdownFinishListener countdownFinishListener){
//        setClickable(false);开始动画之后不可点击
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);
        //动画时长，让进度条在CountDown时间内正好从0-360走完，这里由于用的是CountDownTimer定时器，倒计时要想减到0则总时长需要多加1000毫秒，所以这里时间也跟着+1000ms
        animator.setDuration(countdownTime+1000);
        animator.setInterpolator(new LinearInterpolator());//匀速
        animator.setRepeatCount(0);//表示不循环，-1表示无限循环
        //值从0-1.0F 的动画，动画时长为countdownTime，ValueAnimator没有跟任何的控件相关联，那也正好说明ValueAnimator只是对值做动画运算，而不是针对控件的，我们需要监听ValueAnimator的动画过程来自己对控件做操作
        //添加监听器,监听动画过程中值的实时变化(animation.getAnimatedValue()得到的值就是0-1.0)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
                 * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
                 * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
                 */
                currentAngle = (float) animation.getAnimatedValue();
                //       Log.e("currentAngle",currentAngle+"");
                invalidate();//实时刷新view，这样我们的进度条弧度就动起来了
            }
        });
        //开启动画
        animator.start();
        //还需要另一个监听，监听动画状态的监听器
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //倒计时结束的时候，需要通过自定义接口通知UI去处理其他业务逻辑
                if(countdownFinishListener != null){
                    countdownFinishListener.countdownFinished();
                }
                if(countdownTime > 0){
                    setClickable(true);
                }else{
                    setClickable(false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        //调用倒计时操作
        countdownMethod();
    }

    //倒计时的方法
    private void countdownMethod(){
        _CountDownTimer = new CountDownTimer(countdownTime + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTime = countdownTime - 1000;
//                textDesc = countdownTime/1000 + "″";
//                Log.e("time",countdownTime+"");
                //刷新view
                invalidate();
            }
            @Override
            public void onFinish() {
                //textDesc = 0 + "″";
                textDesc = "跳过";
                //同时隐藏小球
                smallCirclePaint.setColor(getResources().getColor(android.R.color.transparent));
                smallCircleSolidePaint.setColor(getResources().getColor(android.R.color.transparent));
                //刷新view
                invalidate();
            }
        }.start();
    }


    public void cancel(){
        if (_CountDownTimer != null)
        _CountDownTimer.cancel();
    }

    //通过自定义接口通知UI去处理其他业务逻辑
    public interface OnCountdownFinishListener{
        void countdownFinished();
    }

}
