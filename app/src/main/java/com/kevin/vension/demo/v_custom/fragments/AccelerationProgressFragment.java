package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.acceleration_progress.AccelerationProgress;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class AccelerationProgressFragment extends BaseCustomViewFragment {
	private static final String TAG = "MainActivity";
	private static final int LOADING_MSG = 0x001;
	private static final int COMPLETED_MSG = 0x002;
	private static final int COUNTDOWN_MSG = 0x003;
	private static final int DELAY_TIME = 5 * 1000;

	@BindView(R.id.acc_progress_loading_complete)
	AccelerationProgress accProgressLoadingComplete;
	@BindView(R.id.acc_progress_loading)
	AccelerationProgress accProgressLoading;
	@BindView(R.id.acc_progress_loading_time)
	AccelerationProgress accProgressLoadingTime;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_acceleration_progress;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		accProgressLoadingComplete.startLoading();
		accProgressLoading.startLoading();
		//10 seconds
		accProgressLoadingTime.setCountDownTime(10);
		accProgressLoadingTime.setCountdownCompleteListener(new AccelerationProgress.CountdownCompleteListener() {
			@Override
			public void countdownComplete() {
				mHandler.sendEmptyMessageDelayed(COUNTDOWN_MSG, 2 * 1000);
			}
		});
		accProgressLoadingTime.startLoading();
		mHandler.sendEmptyMessageDelayed(COMPLETED_MSG, DELAY_TIME);
	}

	@Override
	public void lazyLoadData() {

	}

	@Override
	public void onPause() {
		super.onPause();
		accProgressLoadingComplete.stopLoading();
		accProgressLoading.stopLoading();
		accProgressLoadingTime.stopLoading();
		mHandler.removeCallbacksAndMessages(null);
	}

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case LOADING_MSG:
					accProgressLoadingComplete.startLoading();
					mHandler.sendEmptyMessageDelayed(COMPLETED_MSG, DELAY_TIME);
					break;

				case COMPLETED_MSG:
					accProgressLoadingComplete.loadCompleted(true);
					mHandler.sendEmptyMessageDelayed(LOADING_MSG, DELAY_TIME);
					break;
				case COUNTDOWN_MSG:
					accProgressLoadingTime.startLoading();
					break;
			}
		}
	};

}
