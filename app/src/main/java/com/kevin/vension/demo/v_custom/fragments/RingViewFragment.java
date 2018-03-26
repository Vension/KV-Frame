package com.kevin.vension.demo.v_custom.fragments;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.RingView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class RingViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.rv_view)
	RingView rvView;
	@BindView(R.id.btn_start)
	Button btnStart;
	@BindView(R.id.rl_content)
	RelativeLayout rlContent;

	ArgbEvaluator evaluator;

	private int startColor = 0XFFfb5338;
	private int centerColor = 0XFF00ff00;
	private int endColor = 0XFF008dfc;
	private int endUseColor = 0;

	List<Integer> valueList = new ArrayList<>();
	List<String> valueNameList = new ArrayList<>();
	private int animDuration = 2500;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_ringview;
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
		evaluator = new ArgbEvaluator();
		valueList.add(350);
		valueList.add(450);
		valueList.add(550);
		valueList.add(650);
		valueList.add(750);
		valueList.add(850);
		rvView.setValueList(valueList);
		valueNameList.add("较差");
		valueNameList.add("中等");
		valueNameList.add("良好");
		valueNameList.add("优秀");
		valueNameList.add("极好");
		rvView.setValueNameList(valueNameList);
//        ly_content.setBackgroundColor((Integer) evaluator.evaluate(0f, startColor, endColor));
		start((int) (350 + Math.random() * 500));
	}


	@OnClick(R.id.btn_start)
	public void onClick(){
		start((int) (350 + Math.random() * 500));
	}


	private void start(int value) {
		float f = (value - valueList.get(0)) * 1.0f / (valueList.get(valueList.size() - 1) - valueList.get(0));
		if (f <= 0.5f) {
			endUseColor = (Integer) evaluator.evaluate(f, startColor, centerColor);
		} else {
			endUseColor = (Integer) evaluator.evaluate(f, centerColor, endColor);
		}

		rvView.setValue(value, new RingView.OnProgerssChange() {
			@Override
			public void OnProgerssChange(float interpolatedTime) {
				int evaluate = 0;
				if (interpolatedTime <= 0.5f) {
					evaluate = (Integer) evaluator.evaluate(interpolatedTime, startColor, endUseColor);
				} else {
					evaluate = (Integer) evaluator.evaluate(interpolatedTime, centerColor, endUseColor);
				}
				rlContent.setBackgroundColor(evaluate);
			}
		},(int)(f*animDuration));
	}



}
