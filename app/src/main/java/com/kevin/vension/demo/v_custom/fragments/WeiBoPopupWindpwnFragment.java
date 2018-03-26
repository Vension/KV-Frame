package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.MoreWindow;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/27.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class WeiBoPopupWindpwnFragment extends BaseCustomViewFragment {

	MoreWindow mMoreWindow;

	@BindView(R.id.show)
	ImageView show;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_weibo_popup;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		show.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showMoreWindow(v);
			}
		});
	}

	@Override
	public void lazyLoadData() {

	}

	private void showMoreWindow(View view) {
		if (null == mMoreWindow) {
			mMoreWindow = new MoreWindow(getActivity());
			mMoreWindow.init();
		}
		mMoreWindow.showMoreWindow(view,100);
	}

}
