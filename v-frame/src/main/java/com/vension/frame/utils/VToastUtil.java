package com.vension.frame.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import xyz.bboylin.universialtoast.UniversalToast;

/**
 * @author ：Created by vension on 2018/2/27.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 避免同样的信息多次触发重复弹出的问题
 */

public class VToastUtil {

	private static Context sContext;
	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	private VToastUtil() {
		throw new RuntimeException("V_ToastUtil cannot be initialized!");
	}

	public static void init(Context context) {
		sContext = context;
	}

	public static void showToast(String s) {
		if (toast == null) {
			toast = Toast.makeText(sContext, s, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
			oneTime = twoTime;
		}
	}

	public static void showToast(int resId) {
		showToast(sContext.getString(resId));
	}

	/** ========================  UniversalToast ================================= */
	public static void showNormal(String msg) {
		showNormal(msg, UniversalToast.EMPHASIZE, Gravity.CENTER);
	}
	public static void showNormal(String msg,int toastType) {
		showNormal(msg,toastType,Gravity.CENTER);
	}
	public static void showNormal(String msg,int toastType,int gravity) {
		UniversalToast.makeText(sContext, msg, UniversalToast.LENGTH_SHORT, toastType)
				.setGravity(gravity,0,0)
				.show();
	}



	public static void showSuccess(String msg) {
		showSuccess(msg,UniversalToast.LENGTH_SHORT,UniversalToast.EMPHASIZE,Gravity.CENTER);
	}
	public static void showSuccess(String msg,int gravity) {
		showSuccess(msg,UniversalToast.LENGTH_SHORT,UniversalToast.EMPHASIZE,gravity);
	}
	private static void showSuccess( String msg, int duration, int toastType, int gravity) {
		UniversalToast.makeText(sContext, msg, duration, toastType)
				.setGravity(gravity,0,0)
				.showSuccess();
	}

	public static void showWarning(String msg) {
		showWarning(msg,UniversalToast.LENGTH_SHORT,UniversalToast.EMPHASIZE,Gravity.CENTER);
	}
	public static void showWarning(String msg,int gravity) {
		showWarning(msg,UniversalToast.LENGTH_SHORT,UniversalToast.EMPHASIZE,gravity);
	}
	private static void showWarning( String msg, int duration, int toastType, int gravity) {
		UniversalToast.makeText(sContext, msg, duration, toastType)
				.setGravity(gravity,0,0)
				.showWarning();
	}

	public static void showError(String msg) {
		showError(msg,UniversalToast.LENGTH_SHORT,UniversalToast.EMPHASIZE,Gravity.CENTER);
	}
	public static void showError(String msg,int gravity) {
		showError(msg,UniversalToast.LENGTH_SHORT,UniversalToast.EMPHASIZE,gravity);
	}
	private static void showError(String msg, int duration, int toastType, int gravity) {
		UniversalToast.makeText(sContext,msg, duration, toastType)
				.setGravity(gravity,0,0)
				.showError();
	}


}
