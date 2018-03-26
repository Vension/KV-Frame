package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.widget.SeekBar;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.PaletteImageView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PaletteImageViewFragment extends BaseCustomViewFragment implements SeekBar.OnSeekBarChangeListener{

	@BindView(R.id.palette)
	PaletteImageView paletteImageView;
	@BindView(R.id.seek1)
	SeekBar seek1;
	@BindView(R.id.seek2)
	SeekBar seek2;
	@BindView(R.id.seek3)
	SeekBar seek3;
	@BindView(R.id.seek4)
	SeekBar seek4;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_palette_imageview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		initListener();
	}

	@Override
	public void lazyLoadData() {

	}

	private void initListener() {
		seek1.setOnSeekBarChangeListener(this);
		seek2.setOnSeekBarChangeListener(this);
		seek3.setOnSeekBarChangeListener(this);
		seek4.setOnSeekBarChangeListener(this);
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		show(seekBar,progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}


	private void show(SeekBar seekBar, int progress) {
		switch (seekBar.getId()){
			case R.id.seek1:
				paletteImageView.setPaletteRadius(progress);
				break;
			case R.id.seek2:
				paletteImageView.setPaletteShadowRadius(progress);
				break;
			case R.id.seek3:
				paletteImageView.setPaletteShadowOffset(progress,0);
				break;
			case R.id.seek4:
				paletteImageView.setPaletteShadowOffset(0,progress);
				break;
		}
	}


}
