package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.TypeTextView;
import com.vension.customview.heartview.HeartView;
import org.jetbrains.annotations.Nullable;
import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class NativeDrawHeartAndTypeTextViewFragment extends BaseCustomViewFragment {
	private static final String LOVE = "***，和你在一起快*年了，一路走来，我们笑过、哭过、疯狂过。。。";

	@BindView(R.id.surfaceView)
	HeartView heartView;
	@BindView(R.id.typeTextView)
	TypeTextView mTypeTextView;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_native_draw_heart;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
//		heartView.reDraw();//画心
		mTypeTextView.start(LOVE);
	}

	@Override
	public void lazyLoadData() {

	}

}
