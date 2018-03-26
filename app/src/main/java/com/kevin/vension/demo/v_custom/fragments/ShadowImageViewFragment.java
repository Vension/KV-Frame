package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vension.customview.Shadow_imageview.ShadowImageView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2018/1/15.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class ShadowImageViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.siv_1)
	ShadowImageView shadow1;
	@BindView(R.id.seekbar)
	AppCompatSeekBar seekBar;
	@BindView(R.id.siv_2)
	ShadowImageView shadow2;

	private int resId = 1;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_shadow_imageview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				shadow1.setImageRadius(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});


		loadNetImage();
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick(R.id.siv_1)
	public void onViewClicked() {
		int res = R.mipmap.img_advertisment;
		switch (resId) {
			case 1:
				res = R.mipmap.img_banner_1;
				resId = 2;
				break;
			case 2:
				res = R.mipmap.img_banner_2;
				resId = 3;
				break;
			case 3:
				res = R.mipmap.img_banner_3;
				resId = 4;
				break;
			case 4:
				res = R.mipmap.img_banner_4;
				resId = 1;
				break;
		}
		if (resId == 1 || resId == 3)
			shadow1.setImageResource(res);
		else
			shadow1.setImageDrawable(getResources().getDrawable(res));
	}



	private void loadNetImage() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(getContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCacheSize(2 * 1024 * 1024)
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(getContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();

		ImageLoader.getInstance().init(config);

		//此处加载的是本地的图片，网络图片用法一至
		ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.img_banner_1, new ImageView(getContext()), new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				shadow2.setImageBitmap(loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
			}
		});
	}

}
