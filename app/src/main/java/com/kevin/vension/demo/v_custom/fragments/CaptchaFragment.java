package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.captach.RxCaptcha;
import com.vension.customview.captach.SwipeCaptcha;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vension.customview.captach.RxCaptcha.TYPE.CHARS;


/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class CaptchaFragment extends BaseCustomViewFragment {

	@BindView(R.id.tv_code)
	TextView tvCode;
	@BindView(R.id.iv_code)
	ImageView ivCode;
	@BindView(R.id.btn_get_code)
	Button btnGetCode;
	@BindView(R.id.swipeCaptchaView)
	SwipeCaptcha mRxSwipeCaptcha;
	@BindView(R.id.dragBar)
	SeekBar mSeekBar;
	@BindView(R.id.btnChange)
	Button mBtnChange;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_captcha;
	}

	@Override
	public void initViewAndData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		initView();
	}

	@Override
	public void lazyLoadData() {

	}

	private void initView() {
		mRxSwipeCaptcha.setOnCaptchaMatchCallback(new SwipeCaptcha.OnCaptchaMatchCallback() {
			@Override
			public void matchSuccess(SwipeCaptcha rxSwipeCaptcha) {
//                RxToast.success(mContext, "验证通过！", Toast.LENGTH_SHORT).show();
				//swipeCaptcha.createCaptcha();
				mSeekBar.setEnabled(false);
			}

			@Override
			public void matchFailed(SwipeCaptcha rxSwipeCaptcha) {
				Log.d("zxt", "matchFailed() called with: rxSwipeCaptcha = [" + rxSwipeCaptcha + "]");
//                RxToast.error(mContext, "验证失败:拖动滑块将悬浮头像正确拼合", Toast.LENGTH_SHORT).show();
				rxSwipeCaptcha.resetCaptcha();
				mSeekBar.setProgress(0);
			}
		});
		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mRxSwipeCaptcha.setCurrentSwipeValue(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				//随便放这里是因为控件
				mSeekBar.setMax(mRxSwipeCaptcha.getMaxSwipeValue());
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Log.d("zxt", "onStopTrackingTouch() called with: seekBar = [" + seekBar + "]");
				mRxSwipeCaptcha.matchCaptcha();
			}
		});

		//测试从网络加载图片是否ok
		Glide.with(getActivity())
				.asBitmap()
				.load(R.drawable.douyu)
				.into(new SimpleTarget<Bitmap>() {
			@Override
			public void onLoadFailed(@Nullable Drawable errorDrawable) {
				super.onLoadFailed(errorDrawable);
			}
			@Override
			public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
				mRxSwipeCaptcha.setImageBitmap(resource);
				mRxSwipeCaptcha.createCaptcha();
			}
		});
	}

	@OnClick({R.id.btn_get_code, R.id.btnChange})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_get_code:
				RxCaptcha.build()
						.backColor(0xffffff)
						.codeLength(6)
						.fontSize(60)
						.lineNumber(0)
						.size(200, 70)
						.type(CHARS)
						.into(ivCode);

				tvCode.setText(RxCaptcha.build().getCode());
				break;
			case R.id.btnChange:
				mRxSwipeCaptcha.createCaptcha();
				mSeekBar.setEnabled(true);
				mSeekBar.setProgress(0);
				break;
		}
	}

}
