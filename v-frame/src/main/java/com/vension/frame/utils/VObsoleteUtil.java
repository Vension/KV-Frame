package com.vension.frame.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

import com.vension.frame.VFrame;

/**
 * @author ：Created by vension on 2018/1/24.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 此类主要是用来放一些系统过时方法的处理
 */

public class VObsoleteUtil {

	private VObsoleteUtil() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	public static float getDimension(Context mContext, @DimenRes int resId) {
		return mContext.getResources().getDimension(resId);
	}


	/** 代码设置Drawable背景*/
	static public Drawable getDrawable(String fillColor) {
		GradientDrawable _GradientDrawable = new GradientDrawable();//创建drawable
		_GradientDrawable.setColor( Color.parseColor(fillColor));
		_GradientDrawable.setCornerRadius(6);
		return _GradientDrawable;
	}


	/**
	 *代码设置left Drawable
	 * */
	public static Drawable setViewDrawable(Context mContext, @DrawableRes int resId) {
		Drawable _Drawable = ContextCompat.getDrawable(mContext,resId);
		_Drawable.setBounds(0, 0, _Drawable.getMinimumWidth(), _Drawable.getMinimumHeight());
		return _Drawable;
	}


	/**
	 * setBackgroundDrawable过时方法处理
	 *
	 * @param view
	 * @param drawable
	 */
	public static void setBackground(@NonNull View view, Drawable drawable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
			view.setBackground(drawable);
		else
			view.setBackgroundDrawable(drawable);
	}

	/**
	 * getDrawable过时方法处理
	 *
	 * @param id
	 * @return
	 */
	public static Drawable getDrawable(@DrawableRes int id) {
		return ContextCompat.getDrawable(VFrame.getContext(), id);
	}

	/**
	 * getDrawable过时方法处理
	 *
	 * @param id 资源id
	 * @param theme 指定主题
	 * @return
	 */
	public static Drawable getDrawable(@DrawableRes int id, @Nullable Resources.Theme theme) {
		return ResourcesCompat.getDrawable(VFrame.getResources(), id, theme);
	}

	/**
	 * getColor过时方法处理
	 *
	 * @param id
	 * @return
	 */
	public static int getColor(@ColorRes int id) {
		return ContextCompat.getColor(VFrame.getContext(), id);
	}

	/**
	 * getColor过时方法处理
	 *
	 * @param id 资源id
	 * @param theme 指定主题
	 * @return
	 */
	public static int getColor(@ColorRes int id, @Nullable Resources.Theme theme) {
		return ResourcesCompat.getColor(VFrame.getResources(), id, theme);
	}

	/**
	 * getColorStateList过时方法处理
	 *
	 * @param id 资源id
	 * @return
	 */
	public static ColorStateList getColorStateList(@ColorRes int id) {
		return ContextCompat.getColorStateList(VFrame.getContext(), id);
	}

	/**
	 * getColorStateList过时方法处理
	 *
	 * @param id 资源id
	 * @param theme 指定主题
	 * @return
	 */
	public static ColorStateList getColorStateList(@ColorRes int id, @Nullable Resources.Theme theme) {
		return ResourcesCompat.getColorStateList(VFrame.getResources(), id, theme);
	}


}
