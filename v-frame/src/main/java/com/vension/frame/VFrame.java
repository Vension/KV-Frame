package com.vension.frame;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import com.vension.frame.http.IHttpEngine;
import com.vension.frame.http.VHttp;
import com.vension.frame.imageloader.ImageLoader;
import com.vension.frame.imageloader.VImage;
import com.vension.frame.utils.VCrashUtil;
import com.vension.frame.utils.VObsoleteUtil;
import com.vension.frame.utils.VSizeUtil;
import com.vension.frame.utils.VToastUtil;
import com.vension.frame.utils.log.VLog;
import com.vension.frame.utils.log.VLogConfig;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：Created by vension on 2018/2/27.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class VFrame {

	private static Application _Application;
	private static Context _Context;
	public static WeakReference<Activity> _TopActivityWeakRef;
	public static List<Activity> _ActivityList = new LinkedList<>();
	public static int screenHeight;
	public static int screenWidth;

	// #log
	public static String tag = "VFrame";
	public static boolean isDebug = true;

	private VFrame() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}


	/**
	 * 初始化工具类
	 *
	 * @param app 应用
	 */
	public static void init(@NonNull final Application app) {
		VFrame._Application = app;
		VFrame._Context = app.getApplicationContext();
		app.registerActivityLifecycleCallbacks(mCallbacks);
		screenHeight = VSizeUtil.getScreenHeight();
		screenWidth = VSizeUtil.getScreenWidth();
	}


	public static VLogConfig initXLog() {return VLog.init();}
	public static void initToast() {
		VToastUtil.init(_Context);
	}
	public static void initCrash() {
		VCrashUtil.init();
	}


	public static void initXImageLoader(ImageLoader loader) {
		VImage.init(loader);
	}

	public static void initXHttp(IHttpEngine httpEngine) {
		VHttp.init(httpEngine);
	}



	public static Context getContext() {
		synchronized (VFrame.class) {
			if (VFrame._Context == null)
				throw new NullPointerException("Call VFrame.init(context) within your Application onCreate() method." +
						"Or extends V_Application");
			return VFrame._Context;
		}
	}

	/**
	 * 获取Application
	 *
	 * @return Application
	 */
	public static Application getApplication() {
		if (_Application != null) return _Application;
		throw new NullPointerException("u should init first");
	}

	public static Resources getResources() {
		return VFrame.getContext().getResources();
	}

	public static String getString(@StringRes int id) {
		return getResources().getString(id);
	}

	public static Resources.Theme getTheme() {
		return VFrame.getContext().getTheme();
	}

	public static AssetManager getAssets() {
		return VFrame.getContext().getAssets();
	}

	public static Drawable getDrawable(@DrawableRes int id) {
		return VObsoleteUtil.getDrawable(id);
	}

	public static int getColor( @ColorRes int id) {
		return VObsoleteUtil.getColor(id);
	}

	public static Object getSystemService(String name){
		return VFrame.getContext().getSystemService(name);
	}

	public static Configuration getConfiguration() {
		return VFrame.getResources().getConfiguration();
	}

	public static DisplayMetrics getDisplayMetrics() {
		return VFrame.getResources().getDisplayMetrics();
	}


	private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
		@Override
		public void onActivityCreated(Activity activity, Bundle bundle) {
			_ActivityList.add(activity);
			setTopActivityWeakRef(activity);
		}

		@Override
		public void onActivityStarted(Activity activity) {
			setTopActivityWeakRef(activity);
		}

		@Override
		public void onActivityResumed(Activity activity) {
			setTopActivityWeakRef(activity);
		}

		@Override
		public void onActivityPaused(Activity activity) {

		}

		@Override
		public void onActivityStopped(Activity activity) {

		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

		}

		@Override
		public void onActivityDestroyed(Activity activity) {
			_ActivityList.remove(activity);
		}
	};


	private static void setTopActivityWeakRef(Activity activity) {
		if (_TopActivityWeakRef == null || !activity.equals(_TopActivityWeakRef.get())) {
			_TopActivityWeakRef = new WeakReference<>(activity);
		}
	}

}
