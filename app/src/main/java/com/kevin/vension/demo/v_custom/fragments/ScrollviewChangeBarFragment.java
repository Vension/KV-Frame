package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class ScrollviewChangeBarFragment extends BaseCustomViewFragment {
	@BindView(R.id.home_top_layout)
	View mTopView;
	@BindView(R.id.home_scroll_view)
	NestedScrollView mScrollView;
	@BindView(R.id.home_title_bar)
	View mTitleBarView;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_scrool_change_bar_bg;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
			@Override
			public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
				int height = mTopView.getHeight();

				float alpha = 1.0f;
				if(mTitleBarView.getTop() + scrollY < height) {
					alpha = scrollY / (float) (height - mTitleBarView.getHeight());
				}
				mTitleBarView.setAlpha(alpha);
			}
		});
	}

	@Override
	public void lazyLoadData() {

	}
}
