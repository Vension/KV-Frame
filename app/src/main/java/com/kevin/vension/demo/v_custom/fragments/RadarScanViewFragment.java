package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.radarscanview.scan_1.RippleView;
import com.vension.customview.radarscanview.scan_1.ScanLayout;
import com.vension.customview.radarscanview.scan_2.RandomTextView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class RadarScanViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.scanlayout)
	ScanLayout scanLayout;
	@BindView(R.id.animator)
	Button animator;
	@BindView(R.id.test)
	RippleView test;
	@BindView(R.id.random_textview)
	RandomTextView randomTextview;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_radarscan_view;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		initData();
	}

	@Override
	public void lazyLoadData() {

	}

	private void initData() {
		scanLayout.setOnItemClickListener(new ScanLayout.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(getActivity(), "click" + position, Toast.LENGTH_SHORT).show();
			}
		});
		animator.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (scanLayout.isScan()) {
					animator.setText("启动");
					scanLayout.stopScan();
				} else {
					animator.setText("停止");
					scanLayout.startScan();
				}
			}
		});


		randomTextview.setOnRippleViewClickListener(
				new RandomTextView.OnRippleViewClickListener()
				{
					@Override
					public void onRippleViewClicked(View view)
					{
						Toast.makeText(getActivity(), "click" , Toast.LENGTH_SHORT).show();
					}
				});

		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				randomTextview.addKeyWord("彭丽媛");
				randomTextview.addKeyWord("习近平");
				randomTextview.show();
			}
		}, 2 * 1000);
	}

	@Override
	public void onResume() {
		super.onResume();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				RippleView rippleView = new RippleView(getActivity());
				rippleView.setImageResource(R.mipmap.icon_logo);
				scanLayout.addView(rippleView);
			}
		}, 3000);
	}

}
