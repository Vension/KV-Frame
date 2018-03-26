package com.vension.customview.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/5/1.
 * 高度相关工具类
 */
public class DimenUtil {

	/** 根据手机的分辨率从 dp 的单位 转成为 px(像素)*/
	static public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}


	/** 根据手机的分辨率从 px(像素) 的单位 转成为 dp*/
	static public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static float sp2px(Context context, float sp){
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return sp * scale;
	}

	/** 获得屏幕宽度 */
	static public int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/** 获得屏幕高度 */
	static public int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}


	/**
	 * calculates the approximate height of a text, depending on a demo text
	 * avoid repeated calls (e.g. inside drawing methods)
	 *
	 * @param paint
	 * @param demoText
	 * @return
	 */
	public static int calcTextHeight(Paint paint, String demoText) {

		Rect r = new Rect();
		paint.getTextBounds(demoText, 0, demoText.length(), r);
		return r.height();
	}


	/**
	 * calculates the approximate width of a text, depending on a demo text
	 * avoid repeated calls (e.g. inside drawing methods)
	 *
	 * @param paint
	 * @param demoText
	 * @return
	 */
	public static int calcTextWidth(Paint paint, String demoText) {
		return (int) paint.measureText(demoText);
	}


}
