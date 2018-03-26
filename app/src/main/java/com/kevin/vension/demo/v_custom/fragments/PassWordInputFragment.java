package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.base.BaseFragment;
import com.vension.customview.password_view.PwdGestureView;
import com.vension.customview.password_view.PwdInputView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PassWordInputFragment extends BaseFragment {

	@BindView(R.id.pwd_input_view2)
	PwdInputView pwdInputView2;
	@BindView(R.id.pwd_input_view3)
	PwdInputView pwdInputView3;
	@BindView(R.id.pwd_input_view)
	PwdInputView pwdInputView;
	@BindView(R.id.sw_show)
	Switch swShow;
	@BindView(R.id.rb_icon)
	RadioButton rbIcon;
	@BindView(R.id.rb_text)
	RadioButton rbText;
	@BindView(R.id.rb_pwd)
	RadioButton rbPwd;
	@BindView(R.id.rg_RadioGroup)
	RadioGroup rgRadioGroup;
	@BindView(R.id.tv_input_pwd)
	TextView tvInputPwd;
	@BindView(R.id.pwd_view)
	PwdGestureView mPwdGestureView;
	@BindView(R.id.sw_line)
	Switch swLine;
	@BindView(R.id.tv_pwd)
	TextView tvPwd;
	@BindView(R.id.cb_set_pwd)
	CheckBox cbSetPwd;
	@BindView(R.id.activity_main)
	ScrollView activityMain;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_password_input;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mPwdGestureView.setIsDrawLine(swLine.isChecked());
		initListeners();
	}

	@Override
	public void lazyLoadData() {

	}

	private void initListeners() {
		swLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mPwdGestureView.setIsDrawLine(isChecked);

			}
		});
		swShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				rbIcon.setEnabled(isChecked);
				rbText.setEnabled(isChecked);
				rbPwd.setEnabled(isChecked);
				showPwd(isChecked);
			}
		});
		rbText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				showPwd(swShow.isChecked());
			}
		});
		rbPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				showPwd(swShow.isChecked());
			}
		});
		rbIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				showPwd(swShow.isChecked());
			}
		});

		mPwdGestureView.startWork(new PwdGestureView.GetPwd() {
			@Override
			public void onGetPwd(String pwd) {
				tvPwd.setText(pwd);
			}
		});
		pwdInputView.setShadowPasswords(swShow.isChecked());
		pwdInputView.setPwdInputViewType(PwdInputView.ViewType.DEFAULT);
//        mPwdInputView.setRadiusBg(10);
		pwdInputView.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				tvInputPwd.setText(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		pwdInputView2.setShadowPasswords(swShow.isChecked());
		pwdInputView2.setPwdInputViewType(PwdInputView.ViewType.UNDERLINE);
		pwdInputView2.setRadiusBg(0);

		pwdInputView3.setShadowPasswords(swShow.isChecked());
		pwdInputView3.setPwdInputViewType(PwdInputView.ViewType.BIASLINE);
		pwdInputView3.setRadiusBg(20);




		cbSetPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					mPwdGestureView.setOldPwd("012543");
				} else {
					mPwdGestureView.setOldPwd(null);
				}
			}
		});

	}


	private void showPwd(boolean show) {
		if (show) {
			if (rbIcon.isChecked()) {
				pwdInputView.setShadowPasswords(show, R.mipmap.icon_pwd);
				pwdInputView2.setShadowPasswords(show, R.mipmap.icon_pwd);
			} else if (rbText.isChecked()) {
				pwdInputView.setShadowPasswords(show, "密");
				pwdInputView2.setShadowPasswords(show, "密");

			} else if (rbPwd.isChecked()) {
				pwdInputView.setShadowPasswords(show);
				pwdInputView2.setShadowPasswords(show);
			}
		} else {
			pwdInputView.setShadowPasswords(show);
			pwdInputView2.setShadowPasswords(show);
		}
	}



}
