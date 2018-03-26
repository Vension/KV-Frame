package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.weather.GyroscopeObserver;
import com.vension.customview.weather.WeatherAnimView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class WeatherAnimViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.weather)
	WeatherAnimView weather;
	private GyroscopeObserver gyroscopeObserver = new GyroscopeObserver();

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_weather_animview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		weather.setGyroscopeObserver(gyroscopeObserver);
	}

	@Override
	public void lazyLoadData() {

	}

	@Override
	public void onResume() {
		super.onResume();
		gyroscopeObserver.register(getActivity());
	}

	@Override
	public void onPause() {
		super.onPause();
		gyroscopeObserver.unregister();
	}

}
