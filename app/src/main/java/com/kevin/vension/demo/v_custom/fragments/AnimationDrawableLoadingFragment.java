package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class AnimationDrawableLoadingFragment extends BaseCustomViewFragment {
	@BindView(R.id.iv_animation_drawable_loading)
	ImageView ivAnimationDrawableLoading;

	AnimationDrawable _AnimationDrawable;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_animation_drawable_loading;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		ivAnimationDrawableLoading.setBackgroundResource(R.drawable.anim_topic);
		_AnimationDrawable = (AnimationDrawable) ivAnimationDrawableLoading.getBackground();
		_AnimationDrawable.start();
	}

	@Override
	public void lazyLoadData() {

	}
}
