package com.kevin.vension.demo.v_custom.fragments;

import android.os.Handler;
import android.view.View;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.GADownloadingView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by Vension on 2018/3/26 11:04.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class GADownloadingViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.ga_downloading)
	GADownloadingView mGADownloadingView;


	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			mProgress += 10;
			if (!isSuccess && mProgress >= 60) {
				mGADownloadingView.onFail();
			}
			mGADownloadingView.updateProgress(mProgress);
			mHandler.postDelayed(mRunnable, UPDATE_PROGRESS_DELAY);
		}
	};

	private static final int UPDATE_PROGRESS_DELAY = 500;
	private int mProgress;
	private Handler mHandler = new Handler();
	private boolean isSuccess = true;


	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_galoadingview;
	}

	@Override
	public void lazyLoadData() {

	}


	@OnClick({R.id.show_success,R.id.show_failed})
	public void onClick(View view){
		switch (view.getId()){
			case R.id.show_success:
				mGADownloadingView.releaseAnimation();
				mHandler.removeCallbacks(mRunnable);
				isSuccess = true;
				mProgress = 0;
				mGADownloadingView.performAnimation();
				mGADownloadingView.updateProgress(0);
				mHandler.postDelayed(mRunnable, 0);

				break;
			case R.id.show_failed:
				mGADownloadingView.releaseAnimation();
				mHandler.removeCallbacks(mRunnable);

				isSuccess = false;
				mProgress = 0;
				mGADownloadingView.performAnimation();
				mGADownloadingView.updateProgress(0);
				mHandler.postDelayed(mRunnable, 0);
				break;
				default:break;
		}
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mRunnable);
	}
}
