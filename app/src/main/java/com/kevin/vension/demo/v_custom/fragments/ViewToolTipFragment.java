package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.ViewTooltip;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2018/2/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class ViewToolTipFragment extends BaseCustomViewFragment {

	@BindView(R.id.editText)
	EditText editText;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_viewtooltip;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick({R.id.left, R.id.top, R.id.bottom, R.id.right, R.id.bottomRight, R.id.bottomLeft})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.left:
				ViewTooltip
						.on(editText)
						// .customView(customView)
						.position(ViewTooltip.Position.LEFT)
						.text(getResources().getString(R.string.text_countdown_time))
						.clickToHide(true)
						.autoHide(false, 0)
						.animation(new ViewTooltip.FadeTooltipAnimation(500))
						.onDisplay(new ViewTooltip.ListenerDisplay() {
							@Override
							public void onDisplay(View view) {
								Log.d("ViewTooltip", "onDisplay");
							}
						})
						.onHide(new ViewTooltip.ListenerHide() {
							@Override
							public void onHide(View view) {
								Log.d("ViewTooltip", "onHide");
							}
						})
						.show();
				break;
			case R.id.top:
				ViewTooltip
						.on(editText)
						.position(ViewTooltip.Position.TOP)
						.text(getResources().getString(R.string.text_countdown_time))
						.show();
				break;
			case R.id.bottom:
				final ViewTooltip.TooltipView viewTooltip = ViewTooltip
						.on(editText)
						.color(Color.BLACK)
						.padding(20, 20, 20, 20)
						.position(ViewTooltip.Position.BOTTOM)
						.align(ViewTooltip.ALIGN.START)
						.text(getResources().getString(R.string.text_countdown_time))
						.show();
				break;
			case R.id.right:
				ViewTooltip
						.on(editText)
						.autoHide(true, 1000)
						.position(ViewTooltip.Position.RIGHT)
						.text(getResources().getString(R.string.text_countdown_time))
						.show();
				break;
			case R.id.bottomRight:
				ViewTooltip
						.on(view)
						.color(Color.BLACK)
						.position(ViewTooltip.Position.TOP)
						.text("bottomRight bottomRight bottomRight")
						.show();
				break;
			case R.id.bottomLeft:
				ViewTooltip
						.on(view)
						.color(Color.BLACK)
						.position(ViewTooltip.Position.TOP)
						.text("bottomLeft bottomLeft bottomLeft")
						.show();
				break;
		}
	}

}
