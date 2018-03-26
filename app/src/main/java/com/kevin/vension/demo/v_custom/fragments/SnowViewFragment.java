package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.snowview.SnowView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class SnowViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.snow_view)
	SnowView mSnowView;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_snowview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mSnowView.startSnowAnim(SnowView.SNOW_LEVEL_MIDDLE);
	}

	@Override
	public void lazyLoadData() {

	}
}
