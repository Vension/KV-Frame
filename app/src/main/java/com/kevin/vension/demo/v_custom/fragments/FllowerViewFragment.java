package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.fllower.FllowerAnimation;
import org.jetbrains.annotations.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class FllowerViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.rlt_animation_layout)
	RelativeLayout rltAnimationLayout;
	@BindView(R.id.btn_start)
	Button btnStart;

	private FllowerAnimation fllowerAnimation;


	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_fllower_animation;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		// 撒花初始化
		fllowerAnimation = new FllowerAnimation(getActivity());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		fllowerAnimation.setLayoutParams(params);
		rltAnimationLayout.addView(fllowerAnimation);
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick(R.id.btn_start)
	public void startAnim(){
		// 开始撒花
		fllowerAnimation.startAnimation();
	}

}
