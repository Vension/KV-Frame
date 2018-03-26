package com.kevin.vension.demo.test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kevin.vension.demo.models.TestEntity;

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

	private ArrayList<TestEntity> mDatas;

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
			TestEntity mEntity = mDatas.get(i);

		}

	}

}
