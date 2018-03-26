package com.vension.frame.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vension.frame.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Created by vension on 2018/1/19.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 仿ios的底部弹出dialog
 */

public class VActionSheetDialog {

	private Context context;
	private Dialog dialog;
	private TextView tvTitle;
	private TextView tvCancel;
	private LinearLayout _LayoutContent;
	private ScrollView _ScrollviewContent;
	private boolean showTitle = false;
	private List<SheetItem> sheetItemList;
	private Display display;

	public VActionSheetDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public VActionSheetDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(R.layout.v_dialog_action_sheet, null);

		// 设置Dialog最小宽度为屏幕宽度
		view.setMinimumWidth(display.getWidth());

		// 获取自定义Dialog布局中的控件
		_ScrollviewContent = view.findViewById(R.id.scrollview_content);
		_LayoutContent = view.findViewById(R.id.layout_content);
		tvTitle = view.findViewById(R.id.tv_action_sheet_title);
		tvCancel = view.findViewById(R.id.tv_action_sheet_cancel);
		tvCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);

		return this;
	}

	public VActionSheetDialog setTitle(String title) {
		showTitle = true;
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText(title);
		return this;
	}
	public VActionSheetDialog setTitleColor(int color) {
		showTitle = true;
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setTextColor(color);
		return this;
	}
	public VActionSheetDialog setTitleSize(float size) {
		showTitle = true;
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setTextSize(size);
		return this;
	}

	public VActionSheetDialog setCancelText(String text) {
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText(text);
		return this;
	}
	public VActionSheetDialog setCancelTextColor(int color) {
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setTextColor(color);
		return this;
	}
	public VActionSheetDialog setCancelTextSize(float size) {
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setTextSize(size);
		return this;
	}

	public VActionSheetDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public VActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	/**
	 * @param strItem  条目名称
	 * @param color    条目字体颜色，设置null则默认蓝色
	 * @param listener
	 * @return
	 */
	public VActionSheetDialog addSheetItem(String strItem, SheetItemColor color, OnSheetItemClickListener listener) {
		if (sheetItemList == null) {
			sheetItemList = new ArrayList<SheetItem>();
		}
		sheetItemList.add(new SheetItem(strItem, color, listener));
		return this;
	}

	/**
	 * 设置条目布局
	 */
	private void setSheetItems() {
		if (sheetItemList == null || sheetItemList.size() <= 0) {
			return;
		}

		int size = sheetItemList.size();

		// TODO 高度控制，非最佳解决办法
		// 添加条目过多的时候控制高度
		if (size >= 7) {
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) _ScrollviewContent.getLayoutParams();
			params.height = display.getHeight() / 2;
			_ScrollviewContent.setLayoutParams(params);
		}

		// 循环添加条目
		for (int i = 1; i <= size; i++) {
			final int index = i;
			SheetItem sheetItem = sheetItemList.get(i - 1);
			String strItem = sheetItem.name;
			float textSize = sheetItem.size;
			SheetItemColor color = sheetItem.color;
			final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

			TextView textView = new TextView(context);
			textView.setText(strItem);
			textView.setTextSize(16);
			textView.setGravity(Gravity.CENTER);

			// 背景图片
			if (size == 1) {
				if (showTitle) {
					textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
				} else {
					textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
				}
			} else {
				if (showTitle) {
					if (i >= 1 && i < size) {
						textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
					} else {
						textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
					}
				} else {
					if (i == 1) {
						textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
					} else if (i < size) {
						textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
					} else {
						textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
					}
				}
			}

			// 字体颜色
			if (color == null) {
				textView.setTextColor(Color.parseColor(SheetItemColor.Blue.getName()));
			} else {
				textView.setTextColor(Color.parseColor(color.getName()));
			}
			// 字体大小
			if (textSize > 0) {
				textView.setTextSize(textSize);
			}

			// 高度
			float scale = context.getResources().getDisplayMetrics().density;
			int height = (int) (45 * scale + 0.5f);
			textView.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, height));

			// 点击事件
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.OnSheetItemClick(index);
					dialog.dismiss();
				}
			});

			_LayoutContent.addView(textView);
		}
	}

	public void show() {
		setSheetItems();
		dialog.show();
	}

	public interface OnSheetItemClickListener {
		void OnSheetItemClick(int which);
	}

	public class SheetItem {
		String name;
		float size;
		OnSheetItemClickListener itemClickListener;
		SheetItemColor color;

		public SheetItem(String name, SheetItemColor color, OnSheetItemClickListener itemClickListener) {
			this.name = name;
			this.color = color;
			this.itemClickListener = itemClickListener;
		}

		public SheetItem(String name, float size, OnSheetItemClickListener itemClickListener, SheetItemColor color) {
			this.name = name;
			this.size = size;
			this.itemClickListener = itemClickListener;
			this.color = color;
		}
	}

	public enum SheetItemColor {
		Black("#000000"),Gray("#aaaaaa"),Blue("#037BFF"), Red("#FD4A2E");

		private String name;

		private SheetItemColor(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
