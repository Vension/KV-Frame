package com.vension.frame.utils.scroll_behavior;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by Administrator on 2016/12/6.
 * 添加给Fab的 Behavior 实现当内容控件向下滑动向下隐藏 向上滑动进入
 */

public class ScrollBehavior extends FloatingActionButton.Behavior {
	private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
	private boolean mIsAnimatingOut = false;//fab是否被 压出 默认false 表示显示

	public ScrollBehavior(Context context, AttributeSet attrs) {
		super();
	}

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
		return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
				|| super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
	}

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

		if (dyConsumed > 0 && !this.mIsAnimatingOut) {
			// User scrolled down and the FAB is currently visible -> hide the FAB
			animateOut(child);
		} else if (dyConsumed < 0 && this.mIsAnimatingOut) {
			// User scrolled up and the FAB is currently not visible -> show the FAB
			animateIn(child);
		}
	}

	// Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
	private void animateOut(final FloatingActionButton button) {
		if (Build.VERSION.SDK_INT >= 14) {
			ViewCompat.animate(button).translationY(button.getHeight() + getMarginBottom(button)).setInterpolator(INTERPOLATOR).withLayer()
					.setListener(new ViewPropertyAnimatorListener() {
						public void onAnimationStart(View view) {
							ScrollBehavior.this.mIsAnimatingOut = true;
						}

						public void onAnimationCancel(View view) {
							ScrollBehavior.this.mIsAnimatingOut = false;
						}

						public void onAnimationEnd(View view) {
//							ScrollBehavior.this.mIsAnimatingOut = false;
							view.setVisibility(View.GONE);
						}
					}).start();
		} else {

		}
	}



	// Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
	private void animateIn(FloatingActionButton button) {
		button.setVisibility(View.VISIBLE);
		if (Build.VERSION.SDK_INT >= 14) {
			ViewCompat.animate(button).translationY(0)
					.setInterpolator(INTERPOLATOR).withLayer().setListener(new ViewPropertyAnimatorListener() {
				@Override
				public void onAnimationStart(View view) {
					ScrollBehavior.this.mIsAnimatingOut = false;
				}

				@Override
				public void onAnimationCancel(View view) {
					ScrollBehavior.this.mIsAnimatingOut = true;
				}

				@Override
				public void onAnimationEnd(View view) {
//					ScrollBehavior.this.mIsAnimatingOut = true;
					view.setVisibility(View.VISIBLE);
				}
			}).start();
		} else {

		}
	}

	private int getMarginBottom(View v) {
		int marginBottom = 0;
		final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
		if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
			marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
		}
		return marginBottom;
	}
}