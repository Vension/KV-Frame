package com.kevin.vension.demo.v_custom.fragments.seat;

import android.os.Bundle;
import android.view.View;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import org.jetbrains.annotations.Nullable;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class SeatTableFragment extends BaseCustomViewFragment {
	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_seat_table;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick({R.id.btn_movie, R.id.btn_flight})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_movie:
				//电影选座
				startAgentActivity(SeatMovieFragment.class);
				break;
			case R.id.btn_flight:
				//飞机选座
				startAgentActivity(SeatFlightFragment.class);
				break;
		}
	}

}
