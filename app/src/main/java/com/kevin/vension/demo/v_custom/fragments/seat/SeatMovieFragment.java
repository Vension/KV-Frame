package com.kevin.vension.demo.v_custom.fragments.seat;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.base.BaseFragment;
import com.vension.customview.SeatMovie;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class SeatMovieFragment extends BaseFragment {
	@BindView(R.id.seatView)
	SeatMovie mSeatView;

	@Override
	public void initToolBar(@NotNull CommonTitleBar mCommonTitleBar) {
		super.initToolBar(mCommonTitleBar);
		mCommonTitleBar.getCenterTextView().setText("电影院选座效果");
	}

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_seat_movie;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mSeatView.setScreenName("3号厅荧幕");//设置屏幕名称
		mSeatView.setMaxSelected(8);//设置最多选中

		mSeatView.setSeatChecker(new SeatMovie.SeatChecker() {

			@Override
			public boolean isValidSeat(int row, int column) {
				return !(column == 2 || column == 12);
			}

			@Override
			public boolean isSold(int row, int column) {
				return row == 6 && column == 6;
			}

			@Override
			public void checked(int row, int column) {

			}

			@Override
			public void unCheck(int row, int column) {

			}

			@Override
			public String[] checkedSeatTxt(int row, int column) {
				return null;
			}

		});
		mSeatView.setData(10, 15);
	}

	@Override
	public void lazyLoadData() {

	}
}
