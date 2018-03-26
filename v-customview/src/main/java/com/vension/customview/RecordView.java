package com.vension.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/22 11:26
 * 描  述：自定义录音、播放动画View效果
 * ========================================================
 */

public class RecordView extends View {
	private final String TAG = RecordView.class.getSimpleName();

	private Context mContext;
	//View默认最小宽度
	private static final int DEFAULT_MIN_WIDTH = 500;
	public final static int MODEL_PLAY = 2;
	public final static int MODEL_RECORD = 1;

	//属性相关
	/**
	 * 默认是录制模式
	 * */
	private int model = MODEL_RECORD;
	private String hintText = ""; //计时器提示时间
	private String playHintText = ""; //计时器开始录制提示时间
	private int timeTextColor; //时间文本的颜色
	private int voiceLineColor; //声音波浪的颜色
	private float hintTextSize; //计时器提示文本大小
	private float middleLineHeight;
	private Drawable progressDrawable;//进度条终点图片
	private String unit;

	private Paint mPaint;
	private ArrayList<Path> paths;


	private int padding = 10; //圆环的边距
	private int widthCircle = 5; //圆环的宽度
	private float progress = 0;//总进度360
	private int countdownTime = 9;//倒计时时间，默认时间10秒
	private int countdownTime2 = 9;//倒计时时间，默认时间10秒.这是会变的

	/**
	 * 振幅
	 */
	private float amplitude = 1;
	/**
	 * 音量
	 */
	private float volume = 10;
	private int fineness = 1;
	private float targetVolume = 1;
	private float maxVolume = 100;
	private long lastTime = 0;
	private int lineSpeed = 100;
	private float translateX = 0;
	private boolean isSet = false;
	private boolean canDrawProgress = false;//是否开始画进度条
	/**
	 * 灵敏度
	 */
	private int sensibility = 4;
	private boolean canSetVolume = true;
	private double radiusDot;
	/**
	 * 圆环颜色
	 * */
	private int[] doughnutColors = new int[]{0xFFDAF6FE,0xFF45C3E5,0xFF45C3E5,0xFF45C3E5,0xFF45C3E5};

	private Timer timeTimer = new Timer(true);
	private Timer progressTimer = new Timer(true);
	private TimerTask timeTask;
	private TimerTask progressTask;
	private OnCountDownListener listener;
	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1){
				countdownTime2--;
				if(countdownTime2 == 0){
					listener.onCountDown();
					canSetVolume = false;
					timeTask.cancel();
					postInvalidate();
				}
			}else if(msg.what == 2){
				progress += 360.00/(countdownTime*950.00/5.00);
//                Log.d(TAG,"progress:"+progress);
				if(progress >360){
					targetVolume = 1;
					postInvalidate();
					progressTask.cancel();
				}else
					postInvalidate();
			}
		}
	};


	public RecordView(Context context) {
		this(context,null);
	}

	public RecordView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public RecordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.recordView);
		model = mTypedArray.getInt(R.styleable.recordView_model,MODEL_RECORD);
		hintText = mTypedArray.getString(R.styleable.recordView_hintText);
		playHintText = mTypedArray.getString(R.styleable.recordView_playHintText);
		timeTextColor = mTypedArray.getColor(R.styleable.recordView_timeTextColor,getResources().getColor(R.color.TimeTextColor));
		hintTextSize = mTypedArray.getDimension(R.styleable.recordView_hintTextSize,15);
		middleLineHeight = mTypedArray.getDimension(R.styleable.recordView_middleLineHeight,2);
		progressDrawable = mTypedArray.getDrawable(R.styleable.recordView_progressSrc) == null ?
				getResources().getDrawable(R.mipmap.light_blue) : mTypedArray.getDrawable(R.styleable.recordView_progressSrc);
		voiceLineColor = mTypedArray.getColor(R.styleable.recordView_middleLineColor,getResources().getColor(R.color.RoundFillColor));
		unit = mTypedArray.getString(R.styleable.recordView_unit);
//		mTypedArray.recycle();

		paths = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			paths.add(new Path());
		}

		mPaint = new Paint();
		mPaint.setAntiAlias(true);//消除锯齿
		mPaint.setStyle(Paint.Style.STROKE);
	}


	/**
	 * 当布局为wrap_content时设置默认长宽
	 *
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measure(widthMeasureSpec),measure(heightMeasureSpec));
	}

	private int measure(int origin) {
		int result = DEFAULT_MIN_WIDTH;
		int specMode = MeasureSpec.getMode(origin);
		int specSize = MeasureSpec.getSize(origin);
		if (specMode == MeasureSpec.EXACTLY){
			result = specSize;
		}else{
			if (specMode == MeasureSpec.AT_MOST){
				result = Math.min(result,specSize);
			}
		}
		return result;
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (model == MODEL_RECORD){
			drawDefaultView(canvas);
			drawVoiceLine(canvas);
		}else{
			drawDefaultForPlay(canvas);
			drawVoiceLine2(canvas);
		}
        //开启进度条
		if (canDrawProgress){
			drawProgress(canvas);
		}
	}


	private void drawDefaultView(Canvas canvas) {
		/**画提示的文字*/
		Paint paintHintText = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintHintText.setTextSize(dip2px(mContext,hintTextSize));
		paintHintText.setColor(mContext.getResources().getColor(R.color.RoundHintTextColor));
		//下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
		paintHintText.setTextAlign(Paint.Align.CENTER);
		if (hintText != null && !hintText.equals("")){
			canvas.drawText(hintText,getWidth() / 2,getHeight() / 2 + 50,paintHintText);
		}else{
			canvas.drawText("剩余录制时间", getWidth() / 2, getHeight() / 2 + 50, paintHintText);
		}

		/**画时间*/
		Paint paintTime = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintTime.setTextSize(dip2px(mContext,60));
		paintTime.setColor(mContext.getResources().getColor(R.color.TimeTextColor));
		paintTime.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(countdownTime2 + "",getWidth() / 2,getHeight() / 2 - 20,paintTime);

		/**画单位，默认S*/
		Paint paintUnit = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintUnit.setTextSize(dip2px(mContext,40));
		paintUnit.setColor(mContext.getResources().getColor(R.color.TimeTextColor));
		paintUnit.setTextAlign(Paint.Align.CENTER);
		float timeWidth = getWidth() / 2f + paintTime.measureText(countdownTime2 + "") * 2 / 3;
		canvas.drawText(unit == null ? "s" : unit,timeWidth,getHeight() / 2 - 20,paintUnit);

		/**画一个大圆（纯色）*/
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(dip2px(mContext,widthCircle));
		mPaint.setColor(mContext.getResources().getColor(R.color.RoundColor));
		RectF oval = new RectF(dip2px(mContext,padding),dip2px(mContext,padding),
				getWidth() - dip2px(mContext,padding),getHeight() - dip2px(mContext,padding));
	    canvas.drawArc(oval,progress,360,false,mPaint);//绘制圆弧
	}

	/**
	 *  画声纹（录制）
	 */
	private void drawVoiceLine(Canvas canvas) {
		lineChange();//线条变化
		mPaint.setColor(voiceLineColor);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(2);
		canvas.save();

		int moveY = getHeight() * 3/4;
		for (int i = 0; i < paths.size(); i++) {
			paths.get(i).reset();
			paths.get(i).moveTo(getWidth() * 5 / 6,getHeight() * 3 / 4);
		}

		for (float j = getWidth() * 5 / 6 - 1; j >= getWidth()/6 ; j -= fineness) {
			float i = j - getWidth() / 6;
			//这边必须保证起始点和终点的时候amplitude = 0;
			amplitude = 5 * volume * i / getWidth() - 5 * volume * i / getWidth() * i / getWidth() * 6 / 4;
			for (int n = 1;n <= paths.size();n++){
				float sin = amplitude * (float) Math.sin((i - Math.pow(1.22, n)) * Math.PI / 180 - translateX);
				paths.get(n - 1).lineTo(j,(2 * n * sin / paths.size() - 15 * sin / paths.size() + moveY));
			}
		}

		for (int n = 0;n < paths.size();n++){
			if (n == paths.size() - 1){
				mPaint.setAlpha(255);
			}else{
				mPaint.setAlpha(n * 130 / paths.size());
			}
			if (mPaint.getAlpha() > 0){
				canvas.drawPath(paths.get(n),mPaint);
			}
		}
		canvas.restore();
	}

	private void lineChange() {
		if (lastTime == 0){
			lastTime = System.currentTimeMillis();
			translateX += 5;
		}else{
			if (System.currentTimeMillis() - lastTime > lineSpeed){
				lastTime = System.currentTimeMillis();
				translateX += 5;
			}else{
				return;
			}
		}

		if (volume < targetVolume && isSet){
			volume += getHeight() / 30;
		}else{
			isSet = false;
			if (volume <= 10){
				volume = 10;
			}else{
				if (volume < getHeight() / 30){
					volume -= getHeight() / 60;
				}else{
					volume -= getHeight() / 30;
				}
			}
		}
	}

	private void drawDefaultForPlay(Canvas canvas) {
       /**先画一个大圆*/
       mPaint.setStyle(Paint.Style.STROKE);
       mPaint.setStrokeWidth(dip2px(mContext,widthCircle));
       mPaint.setColor(mContext.getResources().getColor(R.color.RoundColor));
       RectF ovalBig = new RectF(dip2px(mContext,padding),dip2px(mContext,padding),
			   getWidth() - dip2px(mContext,padding),getHeight() - dip2px(mContext,padding));
       canvas.drawArc(ovalBig,0,360,false,mPaint);

       /**画播放的点*/
       drawImageDot(canvas);

       /**画时间*/
       Paint paintTime2 = new Paint(Paint.ANTI_ALIAS_FLAG);
       paintTime2.setTextSize(dip2px(mContext,14));
       paintTime2.setColor(mContext.getResources().getColor(R.color.RoundFillColor));
       paintTime2.setTextAlign(Paint.Align.CENTER);
       paintTime2.setAntiAlias(true);
		if(playHintText == null){
			playHintText = "正在播放录音.";
		}
	   canvas.drawText(playHintText,getWidth() / 2,getHeight() * 1/3,paintTime2);
	}

	/**
	 * 画一个点（图片）
	 * @param canvas
	 */
	private void drawImageDot(Canvas canvas) {
		if (radiusDot > 0){
			if (progress > 360){
				return;
			}
			double hu = Math.PI * Double.parseDouble(String.valueOf(progress)) / 180.0;
			Log.d(TAG,"hu: " + hu);
			double p = Math.sin(hu) * radiusDot;
			Log.d(TAG,"p: " + p);
			double q = Math.cos(hu) * radiusDot;
			Log.d(TAG,"q: " + q);
			float x = (float) ((getWidth() - progressDrawable.getIntrinsicWidth()) / 2f + p);
			Log.d(TAG,"x: " + x);
			float y = (float) ((dip2px(mContext,padding) - progressDrawable.getIntrinsicHeight() / 2f) + radiusDot - q);
			Log.d(TAG,"y: " + y);
			canvas.drawBitmap(((BitmapDrawable)progressDrawable).getBitmap(),x,y,mPaint);
		}
	}


	/**
	 * 画声纹（播放）
	 */
	private void drawVoiceLine2(Canvas canvas) {
        lineChange();
        mPaint.setColor(voiceLineColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        canvas.save();

        int moveY = getHeight() / 2;
        int pandY = getWidth() / 12;
		for (int i = 0; i < paths.size(); i++) {
			paths.get(i).reset();
			paths.get(i).moveTo(getWidth() - pandY,getHeight() / 2);
		}

		for (float j = getWidth() * 11/12 - 1; j >= getWidth()/12 ; j -= fineness) {
			float i = j - getWidth() / 12;
			//这边必须保证起始点和终点的时候amplitude = 0;
			amplitude = 4 * volume * i / getWidth() - 4 * volume * i / getWidth() * i / getWidth() * 12/10;
			for (int n = 1;n <= paths.size();n++){
				float sin = amplitude * (float) Math.sin((i - Math.pow(1.22, n)) * Math.PI / 180 - translateX);
				paths.get(n - 1).lineTo(j, (2 * n * sin / paths.size() - 15 * sin / paths.size() + moveY));
			}
		}

		for (int n = 0; n < paths.size(); n++) {
			if (n == paths.size() - 1) {
				mPaint.setAlpha(255);
			} else {
				mPaint.setAlpha(n * 130 / paths.size());
			}
			if (mPaint.getAlpha() > 0) {
				canvas.drawPath(paths.get(n), mPaint);
			}
		}
		canvas.restore();
	}


	/**
	 * 绘制圆弧
	 */
	private void drawProgress(Canvas canvas) {
		/**这边画进度*/
		if(progress > 90){
			mPaint.setColor(getResources().getColor(R.color.RoundFillColor));
			mPaint.setStrokeWidth(dip2px(mContext, widthCircle));
			RectF oval = new RectF( dip2px(mContext, padding), dip2px(mContext, padding)
					, getWidth() - dip2px(mContext, padding), getHeight() - dip2px(mContext, padding));
			canvas.drawArc(oval, 0, progress - 90, false, mPaint);    //绘制圆弧
			radiusDot = getHeight()/2f-dip2px(mContext,padding);
		}


		/**
		 * 画一个大圆(渐变)
		 */
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.rotate(-90, getWidth() / 2, getHeight() / 2);
		mPaint.setStrokeWidth(dip2px(mContext, widthCircle));
		mPaint.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2, doughnutColors, null));
		RectF oval = new RectF( dip2px(mContext, padding), dip2px(mContext, padding)
				, getWidth()-dip2px(mContext, padding), getHeight()-dip2px(mContext, padding));
		//看这里，其实这里当progress大于90以后就一直只画0-90度的圆环
		canvas.drawArc(oval, 0, progress < 90 ? progress : 90, false, mPaint);    //绘制圆弧
		canvas.rotate(90, getWidth() / 2, getHeight() / 2);
		mPaint.reset();

		drawImageDot(canvas);
	}


	public interface OnCountDownListener{
		void onCountDown();
	}
	public void setOnCountDownListener(OnCountDownListener listener){
		this.listener = listener;
	}


	public void start(){
		//重置计时器显示的时间
		canSetVolume = true;
		canDrawProgress = true;
		progress = 0;
		countdownTime2 = countdownTime;
		//启动定时器
		timeTimer.schedule(timeTask = new TimerTask() {
			public void run() {
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
			}
		}, 1000, 1000);
		progressTimer.schedule(progressTask = new TimerTask() {
			public void run() {
				Message msg = new Message();
				msg.what = 2;
				mHandler.sendMessage(msg);
			}
		},0,5);
	}

	public void cancel(){
		listener.onCountDown();
		canSetVolume = false;
		timeTask.cancel();
		targetVolume = 1;
		postInvalidate();
		progressTask.cancel();
	}

	public void setModel(int model){
		this.model = model;
		postInvalidate();
	}

	public void setCountdownTime(int countdownTime) {
		this.countdownTime = countdownTime;
		this.countdownTime2 = countdownTime;
		postInvalidate();
	}

	public void setVolume(int volume) {
		if(volume >100)
			volume = volume/100;
		volume = volume*2/5;
		if(!canSetVolume)
			return;
		if (volume > maxVolume * sensibility / 30) {
			isSet = true;
			this.targetVolume = getHeight() * volume / 3 / maxVolume;
			Log.d(TAG,"targetVolume: "+targetVolume);
		}
	}


	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}


}
