package com.kevin.vension.demo.v_custom.fragments.seat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.base.BaseFragment;
import com.vension.customview.SeatAirplane;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class SeatFlightFragment extends BaseFragment {

	@BindView(R.id.fsv)
	SeatAirplane mFlightSeatView;
	@BindView(R.id.btn_clear)
	Button mBtnClear;
	@BindView(R.id.btn_zoom)
	Button mBtnZoom;
	@BindView(R.id.btn_goto)
	Button mBtnGoto;

	@Override
	public void initToolBar(@NotNull CommonTitleBar mCommonTitleBar) {
		super.initToolBar(mCommonTitleBar);
		mCommonTitleBar.getCenterTextView().setText("飞机选座效果");
	}

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_seat_air;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mFlightSeatView.setMaxSelectStates(10);
	}

	@Override
	public void lazyLoadData() {

	}

	public void zoom(View v) {
		mFlightSeatView.startAnim(true);
	}


	public void gotoposition(View v) {
		mFlightSeatView.goCabinPosition(SeatAirplane.CabinPosition.Middle);
	}


	public void clear(View v) {
		mFlightSeatView.setEmptySelecting();
	}


	private void setTestData() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j = j + 2) {
				mFlightSeatView.setSeatSelected(j, i);
			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j = j + 2) {
				mFlightSeatView.setSeatSelected(i + 20, j);
			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j = j + 3) {
				mFlightSeatView.setSeatSelected(i + 35, j);
			}
		}


		for (int i = 11; i < 20; i++) {
			for (int j = 0; j < 8; j = j + 4) {
				mFlightSeatView.setSeatSelected(i + 35, j);
			}
		}
		mFlightSeatView.invalidate();
	}


	@OnClick({R.id.btn_clear, R.id.btn_zoom, R.id.btn_goto})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_clear:
				clear(view);
				break;
			case R.id.btn_zoom:
				zoom(view);
				break;
			case R.id.btn_goto:
				gotoposition(view);
				break;
		}
	}

}
