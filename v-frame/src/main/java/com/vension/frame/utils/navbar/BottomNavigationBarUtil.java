package com.vension.frame.utils.navbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/**
 * @author ：Created by vension on 2018/2/28.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 底部工具栏工具
 */

public class BottomNavigationBarUtil {

	public static StateListDrawable createIconStateListDrawable(Context mContext, int nResId, int pResId) {
		Drawable fNormalDrawable = new BitmapDrawable(mContext.getResources(), BitmapFactory.decodeResource(mContext.getResources(), nResId));
		Drawable fPressedDrawable = new BitmapDrawable(mContext.getResources(), BitmapFactory.decodeResource(mContext.getResources(), pResId));
		StateListDrawable mStateListDrawable = new StateListDrawable();
		mStateListDrawable.addState(new int[]{android.R.attr.state_selected, android.R.attr.state_focused}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_selected, android.R.attr.state_pressed}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_focused}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_pressed}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_selected}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_window_focused}, fNormalDrawable);
		mStateListDrawable.addState(new int[]{}, fNormalDrawable);
		return mStateListDrawable;
	}

	public static StateListDrawable createIconStateListDrawable1(Context mContext, String nPath, String pPath) {
		Drawable fNormalDrawable = new BitmapDrawable(mContext.getResources(), BitmapFactory.decodeFile(nPath));
		Drawable fPressedDrawable = new BitmapDrawable(mContext.getResources(), BitmapFactory.decodeFile(pPath));
		StateListDrawable mStateListDrawable = new StateListDrawable();
		mStateListDrawable.addState(new int[]{android.R.attr.state_selected, android.R.attr.state_focused}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_selected, android.R.attr.state_pressed}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_focused}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_pressed}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_selected}, fPressedDrawable);
		mStateListDrawable.addState(new int[]{android.R.attr.state_window_focused}, fNormalDrawable);
		mStateListDrawable.addState(new int[]{}, fNormalDrawable);
		return mStateListDrawable;
	}

	public static ColorStateList createTextColorStateListDrawable(int nColor, int pColor) {
		int[] colors = new int[]{pColor, pColor, pColor, pColor, pColor, nColor, nColor};
		int[][] states = new int[7][];
		states[0] = new int[]{android.R.attr.state_selected, android.R.attr.state_focused};
		states[1] = new int[]{android.R.attr.state_selected, android.R.attr.state_pressed};
		states[2] = new int[]{android.R.attr.state_focused};
		states[3] = new int[]{android.R.attr.state_pressed};
		states[4] = new int[]{android.R.attr.state_selected};
		states[5] = new int[]{android.R.attr.state_window_focused};
		states[6] = new int[]{};
		ColorStateList mColorStateList = new ColorStateList(states, colors);
		return mColorStateList;
	}

}
