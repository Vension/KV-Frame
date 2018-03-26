package com.kevin.vension.demo.v_custom.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.alertview.AlertView;
import com.vension.customview.alertview.OnDismissListener;
import com.vension.customview.alertview.OnItemClickListener;
import org.jetbrains.annotations.Nullable;

import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/28.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class AlertViewDialogFragment extends BaseCustomViewFragment implements OnItemClickListener, OnDismissListener {

	private AlertView mAlertView;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
	private AlertView mAlertViewExt;//窗口拓展例子
	private EditText etName;//拓展View内容
	private InputMethodManager imm;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_alertview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		mAlertView = new AlertView("标题", "内容", "取消", new String[]{"确定"},
				null, getActivity(), AlertView.Style.Alert, this)
				.setCancelable(true)
				.setOnDismissListener(this);
		//拓展窗口
		mAlertViewExt = new AlertView("提示", "请完善你的个人资料！", "取消", null, new String[]{"完成"},
				getActivity(), AlertView.Style.Alert, this);

		ViewGroup extView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.alertext_form, null);
		etName = (EditText) extView.findViewById(R.id.etName);
		etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean focus) {
				//输入框出来则往上移动
				boolean isOpen = imm.isActive();
				mAlertViewExt.setMarginBottom(isOpen && focus ? 120 : 0);
				System.out.println(isOpen);
			}
		});
		mAlertViewExt.addExtView(extView);
	}

	@Override
	public void lazyLoadData() {

	}

	@Override
	public void onItemClick(Object o, int position) {
		closeKeyboard();
		//判断是否是拓展窗口View，而且点击的是非取消按钮
		if (o == mAlertViewExt && position != AlertView.CANCELPOSITION) {
			String name = etName.getText().toString();
			if (name.isEmpty()) {
				Toast.makeText(getContext(), "啥都没填呢", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getContext(), "hello," + name, Toast.LENGTH_SHORT).show();
			}

			return;
		}
		Toast.makeText(getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDismiss(Object o) {
		closeKeyboard();
		Toast.makeText(getContext(), "消失了", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mAlertView != null && mAlertView.isShowing()) {
				mAlertView.dismiss();
			}
	}


	@OnClick({R.id.btn_alertShow1, R.id.btn_alertShow2, R.id.btn_alertShow3, R.id.btn_alertShow4, R.id.btn_alertShow5, R.id.btn_alertShow6, R.id.btn_alertShowExt})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.btn_alertShow1:
				alertShow1();
				break;
			case R.id.btn_alertShow2:
				alertShow2();
				break;
			case R.id.btn_alertShow3:
				alertShow3();
				break;
			case R.id.btn_alertShow4:
				alertShow4();
				break;
			case R.id.btn_alertShow5:
				alertShow5();
				break;
			case R.id.btn_alertShow6:
				alertShow6();
				break;
			case R.id.btn_alertShowExt:
				alertShowExt();
				break;
		}
	}



	public void alertShow1() {
		mAlertView.show();
	}

	public void alertShow2() {
		new AlertView("标题", "内容", null, new String[]{"确定"},
				null, getContext(), AlertView.Style.Alert, this).show();
	}

	public void alertShow3() {
		new AlertView(null, null, null, new String[]{"高亮按钮1", "高亮按钮2", "高亮按钮3"},
				new String[]{"其他按钮1", "其他按钮2", "其他按钮3", "其他按钮4", "其他按钮5", "其他按钮6",
						"其他按钮7", "其他按钮8"},
				getContext(), AlertView.Style.Alert, this).show();
	}

	public void alertShow4() {
		new AlertView("标题", null, "取消", new String[]{"高亮按钮1"},
				new String[]{"其他按钮1", "其他按钮2", "其他按钮3"},
				getContext(), AlertView.Style.ActionSheet, this).show();
	}

	public void alertShow5() {
		new AlertView("标题", "内容", "取消", null, null,
				getContext(), AlertView.Style.ActionSheet, this).setCancelable(true).show();
	}

	public void alertShow6() {
		new AlertView("上传头像", null, "取消", null,
				new String[]{"拍照", "从相册中选择"},
				getContext(), AlertView.Style.ActionSheet, this).show();
	}

	public void alertShowExt() {
		mAlertViewExt.show();
	}

	private void closeKeyboard() {
		//关闭软键盘
		imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
		//恢复位置
		mAlertViewExt.setMarginBottom(0);
	}


}
