package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.passcodeview.PasscodeView;
import com.vension.frame.utils.VToastUtil;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2018/1/16.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PasscodeViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.passcodeView)
	PasscodeView passcodeView;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_passcode_view;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		passcodeView.setListener(new PasscodeView.PasscodeViewListener() {
			@Override
			public void onFail() {

			}

			@Override
			public void onSuccess(String number) {
				VToastUtil.showToast("onSuccess");
				getActivity().finish();
			}
		});
	}

	@Override
	public void lazyLoadData() {

	}
}
