package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.periscopelayout.PeriscopeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PeriscopeLayoutFragment extends BaseCustomViewFragment {
	@BindView(R.id.periscope)
	PeriscopeLayout periscope;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_periscope_layout;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick(R.id.btn_start)
	public void onClick() {
		periscope.addHeart();
	}

}
