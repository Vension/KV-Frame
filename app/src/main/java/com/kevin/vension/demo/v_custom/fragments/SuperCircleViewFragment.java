package com.kevin.vension.demo.v_custom.fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.SuperCircleView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class SuperCircleViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.superview)
	SuperCircleView mSuperCircleView;
	@BindView(R.id.tv)
	TextView textView;
	@BindView(R.id.superview2)
	SuperCircleView superview2;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_super_circleview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mSuperCircleView.setShowSelect(false);
		ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
		valueAnimator.setTarget(textView);
		valueAnimator.setDuration(2000);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int i = Integer.valueOf(String.valueOf(animation.getAnimatedValue()));
				textView.setText(i + "");
				mSuperCircleView.setSelect((int) (360 * (i / 100f)));
			}
		});
		valueAnimator.start();
	}

	@Override
	public void lazyLoadData() {

	}
}
