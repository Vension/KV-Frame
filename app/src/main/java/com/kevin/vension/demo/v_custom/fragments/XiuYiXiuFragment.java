package com.kevin.vension.demo.v_custom.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class XiuYiXiuFragment extends BaseCustomViewFragment {

	@BindView(R.id.btn_xiu)
	ImageView mBtnXiu;
	@BindView(R.id.rl_xiu)
	RelativeLayout rlXiu;
	private MediaPlayer mp;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_xiu_yi_xiu;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mp = MediaPlayer.create(getActivity(), R.raw.xiu);
		mBtnXiu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mp.isPlaying()) {
					mp.start();
				}
				waveAnim( rlXiu);
			}
		});
	}

	@Override
	public void lazyLoadData() {

	}

	private void waveAnim(ViewGroup paramViewGroup) {
		if ((getActivity() == null) || (paramViewGroup == null))
			return;
		ImageView localImageView = new ImageView(getActivity());
		localImageView.setImageResource(R.mipmap.wave_xiu);
		RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
		localLayoutParams.width = this.mBtnXiu.getWidth();
		localLayoutParams.height = this.mBtnXiu.getHeight();
		localImageView.setLayoutParams(localLayoutParams);
		localImageView.setX(this.mBtnXiu.getX());
		localImageView.setY(this.mBtnXiu.getY());
		ObjectAnimator localObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(localImageView, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("alpha", new float[]{1.0F, 0.0F}), PropertyValuesHolder.ofFloat("scaleX", new float[]{1.2F, 4.0F}), PropertyValuesHolder.ofFloat("scaleY", new float[]{1.2F, 4.0F})});
		localObjectAnimator.setDuration(1000L);
		AnimatorSet localAnimatorSet = new AnimatorSet();
		localAnimatorSet.playTogether(new Animator[]{localObjectAnimator});
		localAnimatorSet.addListener(new WaveAnimatarListener(localImageView, paramViewGroup));
		paramViewGroup.addView(localImageView, 0);
		localImageView.setVisibility(View.VISIBLE);
		localAnimatorSet.setInterpolator(new LinearInterpolator());
		localAnimatorSet.start();
	}

	private static class WaveAnimatarListener implements Animator.AnimatorListener {
		private View mAniView;
		private ViewGroup mViewGroup;

		public WaveAnimatarListener(View view, ViewGroup viewGroup) {
			mAniView = view;
			mViewGroup = viewGroup;
		}

		@Override
		public void onAnimationStart(Animator animation) {
		}

		@Override
		public void onAnimationEnd(Animator animation) {
			if (mAniView != null && mViewGroup != null) {

				mViewGroup.post(new Runnable() {
					@Override
					public void run() {
						mViewGroup.removeView(mAniView);
					}
				});
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {
		}

		@Override
		public void onAnimationRepeat(Animator animation) {
		}
	}


	@Override
	public void onDestroy() {
		if (mp != null)
			mp.release();
		super.onDestroy();
	}


}
