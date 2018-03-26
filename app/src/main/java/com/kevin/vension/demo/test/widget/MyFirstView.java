package com.kevin.vension.demo.test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/23 16:26
 * 描  述：我的第一个自定义View
 * ========================================================
 */

public class MyFirstView extends View {

	private Paint mPaint;//画笔


	//一般在直接New一个View的时候调用。
	public MyFirstView(Context context) {
		super(context);
		initAttrs();
	}

	//一般在layout文件中使用的时候会调用，关于它的所有属性(包括自定义属性)都会包含在attrs中传递进来。
	public MyFirstView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initAttrs();
	}


	private void initAttrs() {
		//1.创建一个画笔
		mPaint = new Paint();
		//2.初始化画笔
		mPaint.setColor(Color.BLACK);      //设置画笔颜色
		mPaint.setStyle(Paint.Style.FILL); //设置画笔模式为填充 1、STROKE：描边  2、FILL：填充 3、FILL_AND_STROKE：描边加填充
		mPaint.setStrokeWidth(10f);        //设置画笔宽度为10px
	}


	/**
	 * 测量View大小(onMeasure)
	 * Q: 为什么要测量View大小？
	 * A: View的大小不仅由自身所决定，同时也会受到父控件的影响，为了我们的控件能更好的适应各种情况，一般会自己进行测量。
	 *  int widthsize  MeasureSpec.getSize(widthMeasureSpec);      //取出宽度的确切数值
	 *  int widthmode  MeasureSpec.getMode(widthMeasureSpec);      //取出宽度的测量模式
	 *
	 *  int heightsize  MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
	 *  int heightmode  MeasureSpec.getMode(heightMeasureSpec);    //取出高度的测量模式
	 *
	 *  测量模式 UNSPECIFIED ：默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
	 *         EXACTLY     ：表示父控件已经确切的指定了子View的大小。
	 *         AT_MOST     ：表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
	 *
	 *  注意：如果对View的宽高进行修改了，不要调用 super.onMeasure( widthMeasureSpec, heightMeasureSpec);
	 *       要调用 setMeasuredDimension( widthsize, heightsize); 这个函数。
	 *
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}


	/**
	 * 确定View大小 在视图大小发生改变时调用
	 *
	 * Q: 在测量完View并使用setMeasuredDimension函数之后View的大小基本上已经确定了，那么为什么还要再次确定View的大小呢？
	 * A: 这是因为View的大小不仅由View本身控制，而且受父控件的影响，所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}


	/**
	 *  它用于确定子View的位置，在自定义ViewGroup中会用到，他调用的是子View的layout函数。
	 *
	 *  在自定义ViewGroup中，onLayout一般是循环取出子View，然后经过计算得出各个子View位置的坐标值，然后用以下函数设置子View位置。
	 *  child.layout(l, t, r, b);
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}


	/**
	 * onDraw是实际绘制的部分，也就是我们真正关心的部分，使用的是Canvas绘图。
	 *
	 操作类型	相关API	备注
	 绘制颜色	drawColor, drawRGB, drawARGB	使用单一颜色填充整个画布
	 绘制基本形状	drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc	依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧
	 绘制图片	drawBitmap, drawPicture	绘制位图和图片
	 绘制文本	drawText, drawPosText, drawTextOnPath	依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字
	 绘制路径	drawPath	绘制路径，绘制贝塞尔曲线时也需要用到该函数
	 顶点操作	drawVertices, drawBitmapMesh	通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用
	 画布剪裁	clipPath, clipRect	设置画布的显示区域
	 画布快照	save, restore, saveLayerXxx, restoreToCount, getSaveCount	依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数
	 画布变换	translate, scale, rotate, skew	依次为 位移、缩放、 旋转、错切
	 Matrix(矩阵)	getMatrix, setMatrix, concat	实际上画布的位移，缩放等操作的都是图像矩阵Matrix， 只不过Matrix比较难以理解和使用，故封装了一些常用的方法。
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//绘制颜色是填充整个画布，常用于绘制底色。
		canvas.drawColor(Color.WHITE);

//		drawPointTest(canvas);//画点
//		drawLineTest(canvas);//画线
//		drawRectTest(canvas);//画矩形
		drawOvalAndCircleTest(canvas);//绘制椭圆和圆
//		drawArcTest(canvas); //绘制圆弧
	}


	/**绘制椭圆*/
	private void drawOvalAndCircleTest(Canvas canvas) {
		// 第一种
		RectF rectFOval = new RectF(400, 500, 1000, 800);
		canvas.drawOval(rectFOval, mPaint);
		// 第二种
//		canvas.drawOval(100,100,800,400,mPaint);
		/**绘制圆*/
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(40);     //为了实验效果明显，特地设置描边宽度非常大

// 描边
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(200,200,100,paint);

// 填充
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(200,500,100,paint);

// 描边加填充
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawCircle(200, 800, 100, paint);
	}

	/**画矩形*/
	private void drawRectTest(Canvas canvas) {
		// 第一种
		canvas.drawRect(400, 100, 1000, 400, mPaint);
		// 第二种
//		Rect rect = new Rect(400,100,800,400);
//		canvas.drawRect(rect,mPaint);
		// 第三种
//		RectF rectF = new RectF(400,100,800,400);
//		canvas.drawRect(rectF,mPaint);
		/**绘制圆角矩形*/
		// 第一种
		Paint mPaint1 = new Paint();
		mPaint1.setColor(Color.RED);
		mPaint1.setStyle(Paint.Style.FILL); //设置画笔模式为填充
		mPaint1.setStrokeWidth(10f);        //设置画笔宽度为10px
		RectF rectF = new RectF(400, 100, 1000, 400);
		canvas.drawRoundRect(rectF, 50, 50, mPaint1);
		// 第二种
//		canvas.drawRoundRect(100,100,800,400,30,30,mPaint);
	}


	/**画点*/
	private void drawPointTest(Canvas canvas) {
		canvas.drawPoint(200, 200, mPaint); //在坐标(200,200)位置绘制一个点
		canvas.drawPoints(new float[]{         //绘制一组点，坐标位置由float数组指定
				500, 500,
				500, 600,
				500, 700,
		}, mPaint);
	}

	/**画直线*/
	private void drawLineTest(Canvas canvas) {
		canvas.drawLine(300, 300, 500, 600, mPaint); // 在坐标(300,300)(500,600)之间绘制一条直线
		canvas.drawLines(new float[]{    // 绘制一组线 每四数字(两个点的坐标)确定一条线
				100, 200, 200, 200,
				100, 300, 200, 300,
		}, mPaint);
	}

	/**
	 *  绘制圆弧
	 * startAngle  // 开始角度
	 * sweepAngle  // 扫过角度
	 * useCenter   // 是否使用中心
	 // 第一种
	 //		public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint){}
	 //        第二种
	 //		public void drawArc(float left, float top, float right, float bottom, float startAngle,float sweepAngle, boolean useCenter, @NonNull Paint paint) {}
	 * @param canvas
	 */
	private void drawArcTest(Canvas canvas) {
		RectF rectF = new RectF(100,100,600,600);
// 绘制背景矩形
		mPaint.setColor(Color.GRAY);
		canvas.drawRect(rectF,mPaint);

// 绘制圆弧
		mPaint.setColor(Color.BLUE);
		canvas.drawArc(rectF,0,90,false,mPaint);

//-------------------------------------

		RectF rectF2 = new RectF(100,700,600,1200);
// 绘制背景矩形
		mPaint.setColor(Color.GRAY);
		canvas.drawRect(rectF2,mPaint);

// 绘制圆弧
		mPaint.setColor(Color.BLUE);
		canvas.drawArc(rectF2,0,90,true,mPaint);
	}

//自定义完View之后，一般会对外暴露一些接口，用于控制View的状态等，或者监听View的变化.
}
