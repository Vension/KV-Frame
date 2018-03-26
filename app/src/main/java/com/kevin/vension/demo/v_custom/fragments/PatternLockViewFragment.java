package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.patternlockview.PatternLockView;
import com.vension.customview.patternlockview.listener.PatternLockViewListener;
import com.vension.customview.patternlockview.utils.PatternLockUtils;
import com.vension.customview.patternlockview.utils.ResourceUtils;
import com.vension.frame.utils.VToastUtil;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2018/1/15.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PatternLockViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.profile_image)
	ImageView profileImage;
	@BindView(R.id.profile_name)
	TextView profileName;
	@BindView(R.id.patter_lock_view)
	PatternLockView mPatternLockView;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_pattern_lockview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mPatternLockView.setDotCount(3);
		mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(getContext(), R.dimen.pattern_lock_dot_size));
		mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(getContext(), R.dimen.pattern_lock_dot_selected_size));
		mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(getContext(), R.dimen.pattern_lock_path_width));
		mPatternLockView.setAspectRatioEnabled(true);
		mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
		mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
		mPatternLockView.setDotAnimationDuration(150);
		mPatternLockView.setPathEndAnimationDuration(100);
		mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(getContext(), R.color.white));
		mPatternLockView.setInStealthMode(false);
		mPatternLockView.setTactileFeedbackEnabled(true);
		mPatternLockView.setInputEnabled(true);
		mPatternLockView.addPatternLockListener(mPatternLockViewListener);
	}

	@Override
	public void lazyLoadData() {

	}

	private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
		@Override
		public void onStarted() {
			Log.d(getClass().getName(), "Pattern drawing started");
		}

		@Override
		public void onProgress(List<PatternLockView.Dot> progressPattern) {
			VToastUtil.showToast("Pattern progress: " + PatternLockUtils.patternToString(mPatternLockView, progressPattern));
			Log.d(getClass().getName(), "Pattern progress: " +
					PatternLockUtils.patternToString(mPatternLockView, progressPattern));
		}

		@Override
		public void onComplete(List<PatternLockView.Dot> pattern) {
			VToastUtil.showToast("Pattern complete: " + PatternLockUtils.patternToString(mPatternLockView, pattern));
			Log.d(getClass().getName(), "Pattern complete: " + PatternLockUtils.patternToString(mPatternLockView, pattern));
		}

		@Override
		public void onCleared() {
			VToastUtil.showToast("Pattern has been cleared");
			Log.d(getClass().getName(), "Pattern has been cleared");
		}
	};

}
