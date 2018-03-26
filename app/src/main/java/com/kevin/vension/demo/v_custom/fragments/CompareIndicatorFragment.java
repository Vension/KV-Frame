package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.CompareIndicator;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class CompareIndicatorFragment extends BaseCustomViewFragment {
	@BindView(R.id.CompareIndicator1)
	CompareIndicator CompareIndicator1;
	@BindView(R.id.CompareIndicator2)
	CompareIndicator CompareIndicator2;
	@BindView(R.id.CompareIndicator3)
	CompareIndicator CompareIndicator3;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_compare_indicator;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);

		CompareIndicator1.updateView(10,90);
		CompareIndicator2.updateView(30,70);
		CompareIndicator3.updateView(70,30);
	}

	@Override
	public void lazyLoadData() {

	}
}
