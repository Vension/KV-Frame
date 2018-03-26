package com.kevin.vension.demo.v_custom.activitys;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.base.BaseActivity;
import com.vension.customview.GestureView;
import com.vension.frame.utils.VToastUtil;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class GestureViewActivity extends BaseActivity implements GestureView.GestureCallBack{
	@BindView(R.id.gesture)
	GestureView gestureView;

	@Override
	public boolean showToolBar() {
		return true;
	}


	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_gestureview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		gestureView.setGestureCallBack(this);
		//不调用这个方法会造成第二次启动程序直接进入手势识别而不是手势设置
		gestureView.clearCache();
		gestureView.setMinPointNums(5);
	}

	@Override
	public void requestLoadData() {

	}

	@Override
	public void gestureVerifySuccessListener(int stateFlag, List<GestureView.GestureBean> data, String message, boolean success) {
		if (stateFlag == GestureView.STATE_LOGIN) {
			VToastUtil.showToast("恭喜！密码正确");
		}
	}


}
