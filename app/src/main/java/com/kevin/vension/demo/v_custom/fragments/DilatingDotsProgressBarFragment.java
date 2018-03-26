package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.dotsprogressbar.DilatingDotsProgressBar;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class DilatingDotsProgressBarFragment extends BaseCustomViewFragment implements SeekBar.OnSeekBarChangeListener{

	@BindView(R.id.progress)
	DilatingDotsProgressBar mDilatingDotsProgressBar;
	@BindView(R.id.seekbar_number_dots)
	AppCompatSeekBar seekbarNumberDots;
	@BindView(R.id.textview_num_dots)
	TextView textviewNumDots;
	@BindView(R.id.seekbar_radius)
	AppCompatSeekBar seekbarRadius;
	@BindView(R.id.textview_radius)
	TextView textviewRadius;
	@BindView(R.id.seekbar_spacing)
	AppCompatSeekBar seekbarSpacing;
	@BindView(R.id.textview_spacing)
	TextView textviewSpacing;
	@BindView(R.id.seekbar_animation_duration)
	AppCompatSeekBar seekbarAnimationDuration;
	@BindView(R.id.textview_animation_duration)
	TextView textviewAnimationDuration;
	@BindView(R.id.seekbar_scale_multiplier)
	AppCompatSeekBar seekbarScaleMultiplier;
	@BindView(R.id.textview_scale_multiplier)
	TextView textviewScaleMultiplier;
	@BindView(R.id.seekbar_color)
	AppCompatSeekBar seekbarColor;
	@BindView(R.id.textview_color)
	TextView textviewColor;

	private final float scaleMin = 1.2f;
	private final float scaleMax = 4.0f;
	private final float saturation = 0.75f;
	private final float value = 0.55f;
	private final int numDotsMin = 2;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_dilating_dotsprogressbar;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mDilatingDotsProgressBar.show(500);
	}

	@Override
	public void lazyLoadData() {

	}

	@Override
	public void onStart() {
		super.onStart();
		setupSeekbars();
	}


	private void setupSeekbars() {
		int dots = mDilatingDotsProgressBar.getNumberOfDots();
		dots = (dots < numDotsMin) ? numDotsMin : dots;
		seekbarNumberDots.setProgress(dots - numDotsMin);
		seekbarNumberDots.setOnSeekBarChangeListener(this);
		textviewNumDots.setText(String.valueOf(dots));

		seekbarRadius.setProgress((int) mDilatingDotsProgressBar.getDotRadius());
		seekbarRadius.setOnSeekBarChangeListener(this);
		textviewRadius.setText(String.valueOf(mDilatingDotsProgressBar.getDotRadius()));

		seekbarSpacing.setProgress((int) mDilatingDotsProgressBar.getHorizontalSpacing());
		seekbarSpacing.setOnSeekBarChangeListener(this);
		textviewSpacing.setText(String.valueOf(mDilatingDotsProgressBar.getHorizontalSpacing()));

		seekbarAnimationDuration.setProgress(mDilatingDotsProgressBar.getDotGrowthSpeed());
		seekbarAnimationDuration.setOnSeekBarChangeListener(this);
		textviewAnimationDuration.setText(String.valueOf(mDilatingDotsProgressBar.getDotGrowthSpeed()));

		int progress = seekbarProgressFromValue(scaleMin, scaleMax, mDilatingDotsProgressBar.getDotScaleMultiplier(),
				seekbarScaleMultiplier.getMax()
		);
		seekbarScaleMultiplier.setProgress(progress);
		seekbarScaleMultiplier.setOnSeekBarChangeListener(this);
		textviewScaleMultiplier.setText(String.valueOf(mDilatingDotsProgressBar.getDotScaleMultiplier()));

		seekbarColor.setOnSeekBarChangeListener(this);
		textviewColor.setText(Integer.toHexString(getHSVColor(0)));
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (seekBar == seekbarRadius) {
			mDilatingDotsProgressBar.setDotRadius(progress);
			textviewRadius.setText(String.valueOf(progress));
		} else if (seekBar == seekbarSpacing) {
			mDilatingDotsProgressBar.setDotSpacing(progress);
			textviewSpacing.setText(String.valueOf(progress));
		} else if (seekBar == seekbarAnimationDuration) {
			mDilatingDotsProgressBar.setGrowthSpeed(progress);
			textviewAnimationDuration.setText(String.valueOf(progress));
		} else if (seekBar == seekbarNumberDots) {
			mDilatingDotsProgressBar.setNumberOfDots(progress + numDotsMin);
			textviewNumDots.setText(String.valueOf(progress + numDotsMin));
		} else if (seekBar == seekbarColor) {
			mDilatingDotsProgressBar.setDotColor(getHSVColor(progress));
			textviewColor.setText(Integer.toHexString(getHSVColor(progress)));
		} else if (seekBar == seekbarScaleMultiplier) {
			float scale = lerp(scaleMin, scaleMax, progress / 100f);
			mDilatingDotsProgressBar.setDotScaleMultpiplier(scale);
			textviewScaleMultiplier.setText(String.format("%.2f", scale));
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}


	private int getHSVColor(int progress) {
		float[] hsvColor = {0, saturation, value};
		hsvColor[0] = 360f * progress / 100f;
		return Color.HSVToColor(hsvColor);
	}

	private float lerp(float min, float max, float progress) {
		return (min * (1.0f - progress)) + (max * progress);
	}

	private int seekbarProgressFromValue(float min, float max, float currentValue, int seekbarMax) {
		float progress = currentValue - min;
		float totalProgress = max - min;
		float progressPercent = progress / totalProgress;
		return (int) (progressPercent * seekbarMax);
	}


}
