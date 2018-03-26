package com.kevin.vension.demo.v_custom.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.carrousel_layout.CarrouselLayout;
import com.vension.customview.carrousel_layout.CarrouselRotateDirection;
import com.vension.customview.carrousel_layout.OnCarrouselItemClickListener;
import com.vension.customview.carrousel_layout.OnCarrouselItemSelectedListener;
import com.vension.frame.utils.VToastUtil;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class CarrouselLayoutFragment extends BaseCustomViewFragment {
	@BindView(R.id.carrousel)
	CarrouselLayout carrousel;
	@BindView(R.id.switchLeftright)
	Switch switchLeftright;
	@BindView(R.id.checkbox)
	CheckBox checkbox;
	@BindView(R.id.seekBarR)
	SeekBar seekBarR;
	@BindView(R.id.seekBarX)
	SeekBar seekBarX;
	@BindView(R.id.seekBarZ)
	SeekBar seekBarZ;
	@BindView(R.id.seekBarTime)
	SeekBar seekBarTime;
	@BindView(R.id.activity_main)
	RelativeLayout activityMain;

	private int width;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_carrousel_layout;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);

		seekBarX.setProgress(seekBarX.getMax() / 2);
		seekBarZ.setProgress(seekBarZ.getMax() / 2);

		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		width=dm.widthPixels;
		carrousel.setR(width/3)//设置R的大小
				.setAutoRotation(false)//是否自动切换
				.setAutoRotationTime(1500);//自动切换的时间  单位毫秒

		initLinstener();
	}

	@Override
	public void lazyLoadData() {

	}

	@Override
	public void onResume() {
		super.onResume();
		//开启自动
		carrousel.resumeAutoRotation();
	}

	@Override
	public void onStop() {
		super.onStop();
		//停止自动
		carrousel.stopAutoRotation();
	}

	private void initLinstener() {
		carrousel.setOnCarrouselItemClickListener(new OnCarrouselItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				VToastUtil.showToast("onItemClick:"+position);
			}

		});
		/**
		 * 选中回调
		 */
		carrousel.setOnCarrouselItemSelectedListener(new OnCarrouselItemSelectedListener() {

			@Override
			public void selected(View view, int position) {
				VToastUtil.showToast("selected:"+position);
			}
		});
		/**
		 * 设置旋转事件间隔
		 */
		seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				long time=(long) (1.0f*progress/seekBar.getMax()*800);
				if(time<=100)time=100;
				carrousel.setAutoRotationTime(time);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		/**
		 * 设置子半径R
		 */
		seekBarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				float r=1.f*progress/seekBar.getMax()*width;
				carrousel.setR(r<=0?1:r);
				carrousel.refreshLayout();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		/**
		 * X轴旋转
		 */
		seekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				carrousel.setRotationX(progress - seekBar.getMax() / 2);
				carrousel.refreshLayout();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		/**
		 * Z轴旋转
		 */
		seekBarZ.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				carrousel.setRotationZ(progress - seekBar.getMax() / 2);
				carrousel.refreshLayout();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		/**
		 * 设置是否自动旋转
		 */
		checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				carrousel.setAutoRotation(isChecked);//启动LoopViewPager自动切换
			}
		});
		/**
		 * 设置向左还是向右自动旋转
		 */
		switchLeftright.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				carrousel.setAutoScrollDirection(isChecked? CarrouselRotateDirection.anticlockwise
						: CarrouselRotateDirection.clockwise);
			}
		});
	}

}
