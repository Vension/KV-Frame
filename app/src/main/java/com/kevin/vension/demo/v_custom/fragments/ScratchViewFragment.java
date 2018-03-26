package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.scratchview.ScratchImageView;
import com.vension.customview.scratchview.ScratchTextView;
import com.vension.frame.utils.VToastUtil;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class ScratchViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.scratch_view)
	ScratchTextView mScratchTextView;
	@BindView(R.id.sample_image)
	ScratchImageView mScratchImageView;


	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_scratvh_view;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		init();
	}

	@Override
	public void lazyLoadData() {

	}

	private void init() {
		if(mScratchTextView != null) {
			mScratchTextView.setRevealListener(new ScratchTextView.IRevealListener() {
				@Override
				public void onRevealed(ScratchTextView tv) {
					mScratchTextView.setText("￥135611");
				}
			});
		}

		mScratchImageView.setRevealListener(new ScratchImageView.IRevealListener() {
			@Override
			public void onRevealed(ScratchImageView tv) {
				VToastUtil.showSuccess("恭喜你，中奖啦");
			}
		});
	}

}
