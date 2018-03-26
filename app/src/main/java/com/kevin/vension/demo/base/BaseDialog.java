package com.kevin.vension.demo.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.kevin.vension.demo.R;
import com.vension.frame.base.V_BaseDialog;

import butterknife.ButterKnife;

/**
 * @author ：Created by vension on 2017/12/21.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public abstract class BaseDialog extends V_BaseDialog {
	public BaseDialog(Context context) {
		super(context);
	}

	public BaseDialog(Context context, int themeId) {
		super(context, themeId);
	}

	protected BaseDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAnimationStyle(R.style.DialogStyleOfDefault);//设置弹窗动画
		setContentView(bindLayoutId());//
		ButterKnife.bind(this);
		final WindowManager.LayoutParams fLayoutParams = getWindow().getAttributes();
		fLayoutParams.dimAmount = 0.3f;
		fLayoutParams.gravity = Gravity.CENTER;
		getWindow().setAttributes(fLayoutParams);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		this.init();
	}

}
