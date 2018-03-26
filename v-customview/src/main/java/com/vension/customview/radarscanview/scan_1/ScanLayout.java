package com.vension.customview.radarscanview.scan_1;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.vension.customview.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andy on 2016/3/15.
 * 扫描界面的展示，添加子View，
 */
public class ScanLayout extends FrameLayout implements View.OnClickListener {

    /*扫描界面环形线的颜色*/
    private int mBaseColor;
    /*扫描线的颜色*/
    private int mScanColor;
    /*环形的个数*/
    private int mNumOfCircle;
    /*环形间隔*/
    private int mRate;
    /*子View的单击事件*/
    private OnItemClickListener mOnItemClickListener;
    private Paint mPaint;

    /*扫描线的Paint*/
    private Paint mScanBarPaint;
    /*扫描动画*/
    private ObjectAnimator mScanAnimator;
    /*动画是否在进行*/
    private boolean isScan;
    /*当前旋转角度*/
    private int currentRotation;
    /*保存当前子View*/
    private ArrayList<View> mChildViews;
    /*保存子View的位置*/
    private ArrayList<Rect> mChildViewLocation;
    /*默认环形的个数*/
    public static final int NUM_CIRCLE = 4;

    public ScanLayout(Context context) {
        this(context, null);
    }

    public ScanLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /*自定义属性初始化*/
        initAttrs(context, attrs);

        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScanLayout);
            mBaseColor = array.getColor(R.styleable.ScanLayout_scanBaseColor,
                    ContextCompat.getColor(context, R.color.material_grey_400));
            mScanColor = array.getColor(R.styleable.ScanLayout_scanColor,
                    ContextCompat.getColor(context, R.color.material_grey_300));
            mNumOfCircle = array.getInt(R.styleable.ScanLayout_numOfCircle, NUM_CIRCLE);
            array.recycle();
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mBaseColor);

        mScanBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScanBarPaint.setStyle(Paint.Style.FILL);
        Shader shader = new SweepGradient(0, 0,
                Color.TRANSPARENT,
                mScanColor);
        mScanBarPaint.setShader(shader);

        mScanAnimator = ObjectAnimator.ofInt(this, "currentRotation", 0, 360);
        mScanAnimator.setDuration(2000);
        mScanAnimator.setInterpolator(new LinearInterpolator());
        mScanAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mScanAnimator.setRepeatMode(ObjectAnimator.RESTART);

        mChildViews = new ArrayList<>();
        mChildViewLocation = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec,widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int realWidth=width-getPaddingLeft()-getPaddingRight();
        /*计算所有子View的宽和高*/
        mRate = realWidth / mNumOfCircle / 2;
        int childSpac = MeasureSpec.makeMeasureSpec(realWidth, MeasureSpec.AT_MOST);
        measureChildren(childSpac,childSpac);
        /*设置自身的宽高*/
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = 0;
        int y = 0;
        int childCount = getChildCount();
        Rect rect;
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (mChildViews.contains(view)) {
                continue;
            }
            do {
                x = random.nextInt(getMeasuredWidth() - view.getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
                y = random.nextInt(getMeasuredHeight() - view.getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
                rect = new Rect(x, y, view.getMeasuredWidth() + x, view.getMeasuredHeight() + y);
                if (null != mOnItemClickListener) {
                    view.setOnClickListener(this);
                }
            } while (Utils.hasViewAt(rect, mChildViewLocation));
            view.layout(rect.left, rect.top, rect.right, rect.bottom);
            mChildViewLocation.add(rect);
            mChildViews.add(view);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        float c = getMeasuredWidth() / 2;
        for (int i = 0; i < mNumOfCircle; i++) {
            canvas.drawCircle(c, c, mRate + i * mRate, mPaint);
        }
        /*将画布的中心点移至布局中心*/
        canvas.translate(c, c);
        /*旋转画布*/
        canvas.rotate(currentRotation);
        /*在改变的画布上画出扫描线*/
        canvas.drawCircle(0, 0, c, mScanBarPaint);
        /*恢复画布的本来面目*/
        canvas.rotate(-currentRotation);
        canvas.translate(-c, -c);
        /*画子View*/
        super.dispatchDraw(canvas);
    }

    public int getCurrentRotation() {
        return currentRotation;
    }

    public void setCurrentRotation(int currentRotation) {
        this.currentRotation = currentRotation;
        this.invalidate();
    }

    public boolean isScan() {
        return isScan;
    }

    /*启动动画*/
    public void startScan() {
        if (!isScan && null != mScanAnimator) {
            mScanAnimator.start();
            isScan = true;
        }
    }

    /*关闭动画*/
    public void stopScan() {
        if (isScan && null != mScanAnimator) {
            mScanAnimator.end();
            isScan = false;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void removeViewAt(int index){
        removeView(mChildViews.remove(index));
    }

    @Override
    public void onClick(View v) {
        if (null != mOnItemClickListener) {
            for (int i = 0; i < mChildViews.size(); i++) {
                if (v.equals(mChildViews.get(i))) {
                    mOnItemClickListener.onItemClick(v, i);
                }
            }
        }
    }

    /*子View的点击事件*/
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
