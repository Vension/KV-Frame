package com.kevin.vension.demo.v_custom.fragments;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.LinearLayout;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.multiChildHistogram.MultiGroupHistogramChildData;
import com.vension.customview.multiChildHistogram.MultiGroupHistogramGroupData;
import com.vension.customview.multiChildHistogram.MultiGroupHistogramView;
import com.vension.customview.utils.DisplayUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * @author ：Created by Administrator on 2018/5/23 10:00.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
public class MultiGroupHistogramViewFragment extends BaseCustomViewFragment {
	private OrientationEventListener orientationEventListener;
	@BindView(R.id.multiGroupHistogramView)
	MultiGroupHistogramView multiGroupHistogramView;

	@BindView(R.id.ll_portrait_screen)
	LinearLayout ll_portrait_screen;
	@BindView(R.id.ll_landscape_screen)
	LinearLayout ll_landscape_screen;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_multigroup_histogramview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		initMultiGroupHistogramView();
		initOrientationListener();
	}

	@Override
	public void lazyLoadData() {

	}


	private void initOrientationListener() {
		orientationEventListener = new OrientationEventListener(getContext()) {
			@Override
			public void onOrientationChanged(int orientation) {
				int screenOrientation = getResources().getConfiguration().orientation;
				if (orientation > 315 || orientation < 45 && orientation > 0) {
					if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
						getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					}
				} else if (orientation > 45 && orientation < 135) {
					if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
						getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
					}
				} else if (orientation > 135 && orientation < 225) {
//                    if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
//                        LogUtil.e("kkkkkkkk: " + orientation);
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
//                    }
				} else if (orientation > 225 && orientation < 315) {
					if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
						getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					}
				}
			}
		};
	}

	@Override
	public void onResume() {
		super.onResume();
		if (orientationEventListener != null) {
			orientationEventListener.enable();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (orientationEventListener != null) {
			orientationEventListener.disable();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		adjustUI(newConfig.orientation);
	}

	private void adjustUI(int orientation) {
		switch (orientation) {
			case Configuration.ORIENTATION_LANDSCAPE: {
				ll_portrait_screen.setVisibility(View.GONE);
				ll_landscape_screen.setVisibility(View.VISIBLE);
				multiGroupHistogramView.getLayoutParams().height = DisplayUtil.dip2px(getContext(),210);
				break;
			}
			case Configuration.ORIENTATION_PORTRAIT: {
				ll_portrait_screen.setVisibility(View.VISIBLE);
				ll_landscape_screen.setVisibility(View.GONE);
				multiGroupHistogramView.getLayoutParams().height = DisplayUtil.dip2px(getContext(),280);
				break;
			}
		}
	}

	private void initMultiGroupHistogramView() {
		Random random = new Random();
		int groupSize = random.nextInt(10) + 10;
		List<MultiGroupHistogramGroupData> groupDataList = new ArrayList<>();
		for (int i = 0; i < groupSize; i++) {
			List<MultiGroupHistogramChildData> childDataList = new ArrayList<>();
			MultiGroupHistogramGroupData groupData = new MultiGroupHistogramGroupData();
			groupData.setGroupName("第" + (i + 1) + "组");
			MultiGroupHistogramChildData childData1 = new MultiGroupHistogramChildData();
			childData1.setSuffix("%");
			childData1.setValue(random.nextInt(50) + 51);
			childDataList.add(childData1);

			MultiGroupHistogramChildData childData2 = new MultiGroupHistogramChildData();
			childData2.setSuffix("分");
			childData2.setValue(random.nextInt(50) + 51);
			childDataList.add(childData2);
			groupData.setChildDataList(childDataList);
			groupDataList.add(groupData);
		}
		multiGroupHistogramView.setDataList(groupDataList);
		int[] color1 = new int[]{Color.parseColor("#FFD100"), Color.parseColor("#FF3300")};

		int[] color2 = new int[]{Color.parseColor("#1DB890"), Color.parseColor("#4576F9")};
		multiGroupHistogramView.setHistogramColor(color1, color2);
	}

}
