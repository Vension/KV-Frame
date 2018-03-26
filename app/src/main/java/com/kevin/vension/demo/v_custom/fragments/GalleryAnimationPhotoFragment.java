package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.galleryanimations.DisplayUtils;
import com.vension.customview.galleryanimations.GalleryAnimationView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/27.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class GalleryAnimationPhotoFragment extends BaseCustomViewFragment {

	@BindView(R.id.photo_surface_view)
	GalleryAnimationView mGalleryAnimationView;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_photo_animation;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayUtils.init(getContext());
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mGalleryAnimationView.setZOrderOnTop(true); //设置画布  背景透明
		mGalleryAnimationView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

		getRootView().findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mGalleryAnimationView.start();
			}
		});
		final Button pauseBtn = getRootView().findViewById(R.id.btn_pause);
		pauseBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mGalleryAnimationView.isRunning() && mGalleryAnimationView.pause()) {
					pauseBtn.setText("Resume");
				} else if (mGalleryAnimationView.isPaused() && mGalleryAnimationView.resume()) {
					pauseBtn.setText("Pause");
				}
			}
		});
		getRootView().findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mGalleryAnimationView.stop();
			}
		});
	}

	@Override
	public void lazyLoadData() {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mGalleryAnimationView.onDestroy();
	}

}
