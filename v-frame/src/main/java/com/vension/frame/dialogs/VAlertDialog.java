package com.vension.frame.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vension.frame.R;

/**
 * @author ：Created by vension on 2018/1/19.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 仿ios的中间弹出dialog
 */

public class VAlertDialog {

	private Context context;
	private Dialog dialog;
	private LinearLayout _LayoutBg;
	private TextView tvTitle;
	private TextView tvContent;
	private Button btnNegative;  //取消按钮
	private Button btnPositive;//确定按钮
	private View _viewLine;
	private Display display;
	private boolean showTitle = false;
	private boolean showContent = false;
	private boolean showPosBtn = false;
	private boolean showNegBtn = false;



	public VAlertDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public VAlertDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(R.layout.v_dialog_alert, null);

		// 获取自定义Dialog布局中的控件
		_LayoutBg = view.findViewById(R.id.layout_bg_alert);
		tvTitle = view.findViewById(R.id.tv_alert_dialog_title);
		tvTitle.setVisibility(View.GONE);
		tvContent = view.findViewById(R.id.tv_alert_dialog_content);
		tvContent.setVisibility(View.GONE);
		btnNegative = view.findViewById(R.id.btn_negative);
		btnNegative.setVisibility(View.GONE);
		btnPositive = view.findViewById(R.id.btn_positive);
		btnPositive.setVisibility(View.GONE);
		_viewLine = view.findViewById(R.id.view_line);
		_viewLine.setVisibility(View.GONE);

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);

		// 调整dialog背景大小
		_LayoutBg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85),
				LinearLayout.LayoutParams.WRAP_CONTENT));
		return this;
	}

	public VAlertDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public VAlertDialog setTitle(CharSequence title) {
		showTitle = true;
		if (TextUtils.isEmpty(title)){
			tvTitle.setText("标题");
		} else {
			tvTitle.setText(title);
		}
		return this;
	}

	public VAlertDialog setTitleColor(int color) {
		showTitle = true;
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setTextColor(color);
		return this;
	}

	public VAlertDialog setTitleSize(float size) {
		showTitle = true;
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setTextSize(size);
		return this;
	}

	public VAlertDialog setContent(CharSequence content) {
		showContent = true;
		if (TextUtils.isEmpty(content)){
			tvContent.setText("内容");
		} else {
			tvContent.setText(content);
		}
		return this;
	}

	public VAlertDialog setContentColor(int color) {
		showContent = true;
		tvContent.setVisibility(View.VISIBLE);
		tvContent.setTextColor(color);
		return this;
	}

	public VAlertDialog setContentSize(float size) {
		showContent = true;
		tvContent.setVisibility(View.VISIBLE);
		tvContent.setTextSize(size);
		return this;
	}

	public VAlertDialog setPositiveButton(String text, final View.OnClickListener listener) {
		setNegativeButton(text, ContextCompat.getColor(context,R.color.app_main_thme_color),listener);
		return this;
	}
	public VAlertDialog setPositiveButton(String text, int color, final View.OnClickListener listener) {
		showPosBtn = true;
		if (TextUtils.isEmpty(text)){
			btnPositive.setText("确定");
		}else{
			btnPositive.setText(text);
		}
		btnPositive.setTextColor(color);
		btnPositive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	public VAlertDialog setNegativeButton(String text, final View.OnClickListener listener) {
		setNegativeButton(text, ContextCompat.getColor(context,R.color.app_main_thme_color),listener);
		return this;
	}
	public VAlertDialog setNegativeButton(String text, int color, final View.OnClickListener listener) {
		showNegBtn = true;
		if (TextUtils.isEmpty(text)){
			btnNegative.setText("取消");
		}else{
			btnNegative.setText(text);
		}
		btnNegative.setTextColor(color);
		btnNegative.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}


	private void setLayout() {
		if (!showTitle && !showContent) {
			tvTitle.setText("提示");
			tvTitle.setVisibility(View.VISIBLE);
		}

		if (showTitle) {
			tvTitle.setVisibility(View.VISIBLE);
		}

		if (showContent) {
			tvContent.setVisibility(View.VISIBLE);
		}

		if (!showPosBtn && !showNegBtn) {
			btnPositive.setText("确定");
			btnPositive.setVisibility(View.VISIBLE);
			btnPositive.setBackgroundResource(R.drawable.alertdialog_single_selector);
			btnPositive.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}

		if (showPosBtn && showNegBtn) {
			btnPositive.setVisibility(View.VISIBLE);
			btnPositive.setBackgroundResource(R.drawable.alertdialog_right_selector);
			btnNegative.setVisibility(View.VISIBLE);
			btnNegative.setBackgroundResource(R.drawable.alertdialog_left_selector);
			_viewLine.setVisibility(View.VISIBLE);
		}

		if (showPosBtn && !showNegBtn) {
			btnPositive.setVisibility(View.VISIBLE);
			btnPositive.setBackgroundResource(R.drawable.alertdialog_single_selector);
		}

		if (!showPosBtn && showNegBtn) {
			btnNegative.setVisibility(View.VISIBLE);
			btnNegative.setBackgroundResource(R.drawable.alertdialog_single_selector);
		}
	}

	public void show() {
		setLayout();
		dialog.show();
	}

}
