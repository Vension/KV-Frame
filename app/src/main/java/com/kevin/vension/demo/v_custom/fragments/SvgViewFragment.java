package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.models.SVG;
import com.vension.customview.svgview.AnimatedSvgView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2018/1/9.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class SvgViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.animated_svg_view)
	AnimatedSvgView animatedSvgView;
	@BindView(R.id.btn_previous)
	Button btnPrevious;
	@BindView(R.id.btn_next)
	Button btnNext;

	private int index = -1;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_svgview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		animatedSvgView.postDelayed(new Runnable() {

			@Override
			public void run() {
				animatedSvgView.start();
			}
		}, 500);

		animatedSvgView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (animatedSvgView.getState() == AnimatedSvgView.STATE_FINISHED) {
					animatedSvgView.start();
				}
			}
		});
		animatedSvgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {

			@Override
			public void onStateChange(@AnimatedSvgView.State int state) {
				if (state == AnimatedSvgView.STATE_TRACE_STARTED) {
					btnPrevious.setEnabled(false);
					btnNext.setEnabled(false);
				} else if (state == AnimatedSvgView.STATE_FINISHED) {
					btnPrevious.setEnabled(index != -1);
					btnNext.setEnabled(true);
					if (index == -1) index = 0; // first time
				}
			}
		});
	}


	@Override
	public void lazyLoadData() {

	}

	@OnClick({R.id.btn_previous, R.id.btn_next})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.btn_previous:
				onPrevious(view);
				break;
			case R.id.btn_next:
				onNext(view);
				break;
		}
	}


	public void onNext(View view) {
		if (++index >= SVG.values().length) index = 0;
		setSvg(SVG.values()[index]);
	}

	public void onPrevious(View view) {
		if (--index < 0) index = SVG.values().length - 1;
		setSvg(SVG.values()[index]);
	}

	private void setSvg(SVG svg) {
		animatedSvgView.setGlyphStrings(svg.glyphs);
		animatedSvgView.setFillColors(svg.colors);
		animatedSvgView.setViewportSize(svg.width, svg.height);
		animatedSvgView.setTraceResidueColor(0x32000000);
		animatedSvgView.setTraceColors(svg.colors);
		animatedSvgView.rebuildGlyphData();
		animatedSvgView.start();
	}

}
