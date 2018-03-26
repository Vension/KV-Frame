package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.waveview.WaveViewByBezier;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2018/3/5.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class WaterFragment extends BaseCustomViewFragment {

	@BindView(R.id.wave_bezier)
	WaveViewByBezier waveViewByBezier;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_water_waveview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		waveViewByBezier.startAnimation();
	}

	@Override
	public void lazyLoadData() {

	}

	@Override
	public void onPause() {
		super.onPause();if (waveViewByBezier != null){
			waveViewByBezier.pauseAnimation();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (waveViewByBezier != null){
			waveViewByBezier.resumeAnimation();
		}
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		if (waveViewByBezier != null){
			waveViewByBezier.stopAnimation();
		}
	}

}
