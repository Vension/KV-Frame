package com.kevin.vension.demo.test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.kevin.vension.demo.test.datas.PieData;

import java.util.ArrayList;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/23 17:57
 * 描  述：学习自定义饼状图
 * ========================================================
 */

public class MyPieView extends View {

	//定义饼状图要用到的颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
	private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080, 0xFFE6B800, 0xFF7CFC00};

	private Paint mPaint = new Paint();//画笔
	private int mWidth,mHeight;//宽高
	private float mStartAngle = 0;// 饼状图初始绘制角度

	// 文字色块部分
	private PointF mStartPoint = new PointF(20, 20);
	private PointF mCurrentPoint = new PointF(mStartPoint.x, mStartPoint.y);
	private float mColorRectSideLength = 20;
	private float mTextInterval = 10;
	private float mRowMaxLength;

	private ArrayList<PieData> mDatas;

	public MyPieView(Context context) {
		this(context,null);
	}

	public MyPieView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	private void initPaint() {
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mDatas.isEmpty())
			return;

		float currentStartAngle = mStartAngle;                          // 当前起始角度
		canvas.translate(mWidth / 2,mHeight / 2);                // 将画布坐标原点移动到中心位置
		float radius = (float) (Math.min(mWidth/2,mHeight/2) / 2 * 0.8);// 饼状图半径
		RectF mRectF = new RectF(-radius,-radius,radius,radius);

		for (int i = 0; i < mDatas.size(); i++) {
			PieData mPieData = mDatas.get(i);
            mPaint.setColor(mPieData.getColor());
			canvas.drawArc(mRectF,currentStartAngle,mPieData.getAngle(),true,mPaint);
			currentStartAngle += mPieData.getAngle();

			canvas.save();
			canvas.translate(-mWidth / 2,-mHeight / 2);
			RectF colorRectF = new RectF(mCurrentPoint.x,mCurrentPoint.y,mCurrentPoint.x + mColorRectSideLength,mCurrentPoint.y + mColorRectSideLength);

			canvas.restore();
		}

	}


	// 设置起始角度
	public void setStartAngle(int mStartAngle) {
		this.mStartAngle = mStartAngle;
		invalidate();   // 刷新
	}

	// 设置数据
	public void setData(ArrayList<PieData> mData) {
		this.mDatas = mData;
		initData(mData);
		invalidate();   // 刷新
	}

	// 初始化数据
	private void initData(ArrayList<PieData> mData) {
		if (null == mData || mData.size() == 0)   // 数据有问题 直接返回
			return;

		float sumValue = 0;
		for (int i = 0; i < mData.size(); i++) {
			PieData pie = mData.get(i);

			sumValue += pie.getValue();       //计算数值和

			int j = i % mColors.length;       //设置颜色
			pie.setColor(mColors[j]);
		}

		float sumAngle = 0;
		for (int i = 0; i < mData.size(); i++) {
			PieData pie = mData.get(i);

			float percentage = pie.getValue() / sumValue;   // 百分比
			float angle = percentage * 360;                 // 对应的角度

			pie.setPercentage(percentage);                  // 记录百分比
			pie.setAngle(angle);                            // 记录角度大小
			sumAngle += angle;

			Log.i("angle", "" + pie.getAngle());
		}
	}

}
