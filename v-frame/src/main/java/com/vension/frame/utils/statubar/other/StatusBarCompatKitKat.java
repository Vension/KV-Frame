package com.vension.frame.utils.statubar.other;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 作者：Administrator on 2016/11/23 14:22
 * 邮箱：Zoran@kewaimiao.com
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class StatusBarCompatKitKat {


	private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
	private static final String TAG_MARGIN_ADDED = "marginAdded";

	/**
	 * return statusBar's Height in pixels
	 */
	private static int getStatusBarHeight(Context context) {
		int result = 0;
		int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resId > 0) {
			result = context.getResources().getDimensionPixelOffset(resId);
		}
		return result;
	}

	/**
	 * 1. Add fake statusBarView.
	 * 2. set tag to statusBarView.
	 */
	private static View addFakeStatusBarView(Activity activity, int statusBarColor, int statusBarHeight) {
		Window window = activity.getWindow();
		ViewGroup mDecorView = (ViewGroup) window.getDecorView();

		View mStatusBarView = new View(activity);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
		layoutParams.gravity = Gravity.TOP;
		mStatusBarView.setLayoutParams(layoutParams);
		mStatusBarView.setBackgroundColor(statusBarColor);
		mStatusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);

		mDecorView.addView(mStatusBarView);
		return mStatusBarView;
	}

	/**
	 * use reserved order to remove is more quickly.
	 */
	private static void removeFakeStatusBarViewIfExist(Activity activity) {
		Window window = activity.getWindow();
		ViewGroup mDecorView = (ViewGroup) window.getDecorView();

		View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
		if (fakeView != null) {
			mDecorView.removeView(fakeView);
		}
	}

	/**
	 * add marginTop to simulate set FitsSystemWindow true
	 */
	private static void addMarginTopToContentChild(View mContentChild, int statusBarHeight) {
		if (mContentChild == null) {
			return;
		}
		if (!TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
			lp.topMargin += statusBarHeight;
			mContentChild.setLayoutParams(lp);
			mContentChild.setTag(TAG_MARGIN_ADDED);
		}
	}

	/**
	 * remove marginTop to simulate set FitsSystemWindow false
	 */
	private static void removeMarginTopOfContentChild(View mContentChild, int statusBarHeight) {
		if (mContentChild == null) {
			return;
		}
		if (TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
			lp.topMargin -= statusBarHeight;
			mContentChild.setLayoutParams(lp);
			mContentChild.setTag(null);
		}
	}

	/**
	 * set StatusBarColor
	 * <p>
	 * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
	 * 2. removeFakeStatusBarViewIfExist
	 * 3. addFakeStatusBarView
	 * 4. addMarginTopToContentChild
	 * 5. cancel ContentChild's fitsSystemWindow
	 */
	static void setStatusBarColor(Activity activity, int statusColor) {
		Window window = activity.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
		View mContentChild = mContentView.getChildAt(0);
		int statusBarHeight = getStatusBarHeight(activity);

		removeFakeStatusBarViewIfExist(activity);
		addFakeStatusBarView(activity, statusColor, statusBarHeight);
		addMarginTopToContentChild(mContentChild, statusBarHeight);

		if (mContentChild != null) {
			ViewCompat.setFitsSystemWindows(mContentChild, false);
		}
	}

	/**
	 * translucentStatusBar
	 * <p>
	 * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
	 * 2. removeFakeStatusBarViewIfExist
	 * 3. removeMarginTopOfContentChild
	 * 4. cancel ContentChild's fitsSystemWindow
	 */
	static void translucentStatusBar(Activity activity) {
		/**
		 * 默认状态栏是透明色
		 * 若想状态栏的颜色是跟随 布局自定义透明 (父布局添加: android:fitsSystemWindows="true")
		 * */
		// 透明导航栏 一些手机如果有虚拟键盘的话,虚拟键盘就会变成透明的,挡住底部按钮点击事件所以,最后不要用
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		Window window = activity.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
		View mContentChild = mContentView.getChildAt(0);

		removeFakeStatusBarViewIfExist(activity);
		removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity));
		if (mContentChild != null) {
			ViewCompat.setFitsSystemWindows(mContentChild, false);
		}
	}

	/**
	 * compat for CollapsingToolbarLayout
	 * <p>
	 * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
	 * 2. set FitsSystemWindows for views.
	 * 3. removeFakeStatusBarViewIfExist
	 * 4. removeMarginTopOfContentChild
	 * 5. add OnOffsetChangedListener to change statusBarView's alpha
	 */
	static void setStatusBarColorForCollapsingToolbar(Activity activity, final AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout,
													  Toolbar toolbar, int statusColor) {
		Window window = activity.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);

		View mContentChild = mContentView.getChildAt(0);
		mContentChild.setFitsSystemWindows(false);
		((View) appBarLayout.getParent()).setFitsSystemWindows(false);
		appBarLayout.setFitsSystemWindows(false);
		collapsingToolbarLayout.setFitsSystemWindows(false);
		collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);

		toolbar.setFitsSystemWindows(true);
		if (toolbar.getTag() == null) {
			CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
			lp.height += getStatusBarHeight(activity);
			toolbar.setLayoutParams(lp);
			toolbar.setTag(true);
		}

		int statusBarHeight = getStatusBarHeight(activity);
		removeFakeStatusBarViewIfExist(activity);
		removeMarginTopOfContentChild(mContentChild, statusBarHeight);
		final View statusView = addFakeStatusBarView(activity, statusColor, statusBarHeight);
		appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
					if (statusView.getAlpha() == 0) {
						statusView.animate().alpha(1f).setDuration(collapsingToolbarLayout.getScrimAnimationDuration()).start();
					}
				} else {
					statusView.setAlpha(0);
				}
			}
		});
	}
}
