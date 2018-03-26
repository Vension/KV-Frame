package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.ArcProgress;
import com.vension.customview.InstrumentView;

import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class DashBoardEffectFragment extends BaseCustomViewFragment {
	@BindView(R.id.iView)
	InstrumentView iView;
	@BindView(R.id.txtView)
	TextView txtView;
	@BindView(R.id.arc_progress)
	ArcProgress mArcProgress;
	private int progress = 0;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_dashboard_effect;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				iView.setReferValue(682, new InstrumentView.RotateListener() {
					@Override
					public void rotate(float sweepAngle, final float value) {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								txtView.setText(Math.round(value) + "");
							}
						});
					}
				});
			}
		},1000);

		mHandler.postDelayed(progressTask, 1000);
	}

	@Override
	public void lazyLoadData() {

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mArcProgress.setProgress(msg.arg1);
			mHandler.postDelayed(progressTask, 1000);
		}
	};

	private Runnable progressTask = new Runnable() {

		@Override
		public void run() {
			progress += 100;
			mHandler.sendMessage(mHandler.obtainMessage(0, progress, 0));
		}
	};


	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(progressTask);
		mHandler = null;
	}

}
