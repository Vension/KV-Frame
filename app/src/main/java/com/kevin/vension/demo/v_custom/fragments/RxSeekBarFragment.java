package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.HorizontalProgressBar;
import com.vension.customview.RxSeekBar;

import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class RxSeekBarFragment extends BaseCustomViewFragment {
	@BindView(R.id.seekbar1)
	RxSeekBar mSeekbar1;
	@BindView(R.id.progress2_tv)
	TextView mProgress2Tv;
	@BindView(R.id.seekbar2)
	RxSeekBar mSeekbar2;
	@BindView(R.id.seekbar3)
	RxSeekBar mSeekbar3;
	@BindView(R.id.seekbar4)
	RxSeekBar mSeekbar4;
	@BindView(R.id.horizontal_progress_view)
	HorizontalProgressBar horizontalProgressBar;

	private DecimalFormat df = new DecimalFormat("0.00");

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_rxseekbar;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mSeekbar1.setValue(10);
		mSeekbar2.setValue(-0.5f, 0.8f);

		mSeekbar1.setOnRangeChangedListener(new RxSeekBar.OnRangeChangedListener() {
			@Override
			public void onRangeChanged(RxSeekBar view, float min, float max, boolean isFromUser) {
				mSeekbar1.setProgressDescription((int) min + "%");
			}
		});

		mSeekbar2.setOnRangeChangedListener(new RxSeekBar.OnRangeChangedListener() {
			@Override
			public void onRangeChanged(RxSeekBar view, float min, float max, boolean isFromUser) {
				if (isFromUser) {
					mProgress2Tv.setText(min + "-" + max);
					mSeekbar2.setLeftProgressDescription(df.format(min));
					mSeekbar2.setRightProgressDescription(df.format(max));
				}
			}
		});

		horizontalProgressBar.setProgressWithAnimation(88).setProgressListener(new HorizontalProgressBar.ProgressListener() {
			@Override
			public void currentProgressListener(float currentProgress) {
			}
		});
		horizontalProgressBar.startProgressAnimation();

	}

	@Override
	public void lazyLoadData() {

	}
}
