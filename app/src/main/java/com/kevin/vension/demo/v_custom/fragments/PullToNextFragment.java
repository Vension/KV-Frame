package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.fragments.drag.VerticalFragment1;
import com.kevin.vension.demo.v_custom.fragments.drag.VerticalFragment3;
import com.vension.customview.pulltonext.DragLayout;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PullToNextFragment extends BaseCustomViewFragment {
	@BindView(R.id.first)
	FrameLayout first;
	@BindView(R.id.second)
	FrameLayout second;
	@BindView(R.id.draglayout)
	DragLayout draglayout;

	private VerticalFragment1 fragment1;
	private VerticalFragment3 fragment3;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_pull_to_next;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		fragment1 = new VerticalFragment1();
		fragment3 = new VerticalFragment3();

		getActivity().getSupportFragmentManager().beginTransaction()
				.add(R.id.first, fragment1).add(R.id.second, fragment3)
				.commit();

		DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
			@Override
			public void onDragNext() {
				fragment3.initView();
			}
		};
		draglayout.setNextPageListener(nextIntf);
	}

	@Override
	public void lazyLoadData() {

	}
}
