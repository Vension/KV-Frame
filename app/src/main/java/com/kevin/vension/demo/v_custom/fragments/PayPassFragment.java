package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.paydialog.PayDialog;

import org.jetbrains.annotations.Nullable;

import butterknife.OnClick;

/**
 * @author ：Created by Vension on 2018/3/21 16:59.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PayPassFragment extends BaseCustomViewFragment implements PayDialog.PayInterface{

	private PayDialog payDialog;
	private boolean status = false;//替代方案，这里没有做网络请求，所以定义一个boolean字段来判断正确还是失败

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_pay_pass;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick({R.id.main_payerror,R.id.main_paysucc})
	public void onClick(View view){
		switch (view.getId()){
			case R.id.main_payerror:
				payDialog = new PayDialog(getContext(),"支付金额：80元",this);
				status = false;
				payDialog.show();
				break;
			case R.id.main_paysucc:
				payDialog = new PayDialog(getContext(),"支付金额：80元",this);
				status = true;
				//设置progress的颜色
				payDialog.setColorId(ContextCompat.getColor(getContext(),R.color.app_main_thme_color));
				payDialog.show();
				break;
			default:
				break;
		}
	}

	@Override
	public void payFinish(String password) {
		//这里是当用户输入密码完成时 得到输入密码的回调方法
		new Handler().postDelayed(() -> {
			//这里是做请求校验的，暂时用停留2秒做假请求
			if(status){
				//请求成功
				payDialog.setSuccess();
			}else{
				//请求失败 传入失败理由
				payDialog.setError("支付密码不正确");
			}
		},2000);
	}

	@Override
	public void onSuccess() {
		//回调 成功，关闭dialog 做自己的操作
		payDialog.cancel();
	}

	@Override
	public void onForget() {
		//当progress显示时，说明在请求网络，这时点击忘记密码不作处理
		if(payDialog.mPayPassView.mMDProgressBar.getVisibility()!= View.VISIBLE){
			Toast.makeText(getContext(),"去找回密码",Toast.LENGTH_SHORT).show();
		}
	}
}
