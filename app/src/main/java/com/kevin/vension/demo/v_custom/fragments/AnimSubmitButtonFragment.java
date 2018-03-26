package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.AnimSubmitButton;
import com.vension.frame.utils.VToastUtil;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class AnimSubmitButtonFragment extends BaseCustomViewFragment {

	@BindView(R.id.btn3)
	AnimSubmitButton btn3;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_anim_submit_btn;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		btn3.setOnClickListener(new View.OnClickListener() {
			public static final String TAG = "onclick";

			@Override
			public void onClick(View v) {
				VToastUtil.showSuccess("onClick: Submit");
				Log.d(TAG, "onClick: Submit");
			}
		});
	}

	@Override
	public void lazyLoadData() {

	}
}
