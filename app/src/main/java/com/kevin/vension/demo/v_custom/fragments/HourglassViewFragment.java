package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.HourglassView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2018/1/8.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class HourglassViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.hv_01)
	HourglassView mHv01;
	@BindView(R.id.hv_02)
	HourglassView mHv02;
	@BindView(R.id.hv_03)
	HourglassView mHv03;
	@BindView(R.id.hv_04)
	HourglassView mHv04;
	@BindView(R.id.btn_start)
	Button btnStart;
	@BindView(R.id.btn_end)
	Button btnEnd;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_hourglassview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		btnStart.setOnClickListener(this);
		btnEnd.setOnClickListener(this);
	}

	@Override
	public void lazyLoadData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_start:
				mHv01.start();
				mHv02.start();
				mHv03.start();
				mHv04.start();
				break;
			case R.id.btn_end:
				mHv01.end();
				mHv02.end();
				mHv03.end();
				mHv04.end();
				break;
		}
	}

}
