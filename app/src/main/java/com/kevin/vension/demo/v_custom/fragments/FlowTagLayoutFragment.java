package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.adapters.FlowTagAdapter;
import com.kevin.vension.demo.v_custom.adapters.FlowTagImgAdapter;
import com.vension.customview.flowtaglayout.FlowTagLayout;
import com.vension.customview.flowtaglayout.OnTagClickListener;
import com.vension.customview.flowtaglayout.OnTagSelectListener;
import com.vension.frame.utils.VToast;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class FlowTagLayoutFragment extends BaseCustomViewFragment {

	@BindView(R.id.single_click_flow_layout)
	FlowTagLayout singleClickFlowLayout;
	@BindView(R.id.single_choose_flow_layout)
	FlowTagLayout singleChooseFlowLayout;
	@BindView(R.id.multi_select_flow_layout)
	FlowTagLayout multiSelectFlowLayout;
	@BindView(R.id.img_flow_layout)
	FlowTagLayout imgFlowLayout;

	private FlowTagAdapter<String> mSizeTagAdapter;
	private FlowTagAdapter<String> mColorTagAdapter;
	private FlowTagAdapter<String> mMobileTagAdapter;
	private FlowTagImgAdapter<String> mImgTagAdapter;


	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_flowtag_layout;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mColorTagAdapter = new FlowTagAdapter<>(getActivity());
		singleClickFlowLayout.setAdapter(mColorTagAdapter);

		mSizeTagAdapter = new FlowTagAdapter<>(getActivity());
		mSizeTagAdapter.setSelected(4);
		singleChooseFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
		singleChooseFlowLayout.setAdapter(mSizeTagAdapter);

		mMobileTagAdapter = new FlowTagAdapter<>(getActivity());
		multiSelectFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
		multiSelectFlowLayout.setAdapter(mMobileTagAdapter);

		mImgTagAdapter = new FlowTagImgAdapter<>(getActivity());
		imgFlowLayout.setAdapter(mImgTagAdapter);

		initColorData();
		initSizeData();
		initMobileData();
		initImageData();
		initListener();
	}

	@Override
	public void lazyLoadData() {

	}

	private void initMobileData() {
		List<String> dataSource = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if (i%3 == 0){
				dataSource.add("多选*-* i%3 == 0"+i);
			}else if (i%3 == 1){
				dataSource.add("多选*-* i%3 == 1"+i);
			}else{
				dataSource.add("多选*-* "+i);
			}
		}
		mMobileTagAdapter.onlyAddAll(dataSource);
	}


	private void initImageData() {
		List<String> dataSource = new ArrayList<>();
		for (int i = 0; i < 36; i++) {
			if (i%3 == 0){
				dataSource.add("多选*-* i%3 == 0"+i);
			}else if (i%3 == 1){
				dataSource.add("多选*-* i%3 == 1"+i);
			}else{
				dataSource.add("多选*-* "+i);
			}
		}
		mImgTagAdapter.onlyAddAll(dataSource);
	}

	private void initColorData() {
		List<String> dataSource = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if (i%3 == 0){
				dataSource.add("单击!+! i%3 == 0"+i);
			}else if (i%3 == 1){
				dataSource.add("单击!+! i%3 == 1"+i);
			}else{
				dataSource.add("单击!+! "+i);
			}
		}
		mColorTagAdapter.onlyAddAll(dataSource);
	}

	/**
	 * 初始化数据
	 */
	private void initSizeData() {
		List<String> dataSource = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if (i%3 == 0){
				dataSource.add("单选^-^ i%3 == 0"+i);
			}else if (i%3 == 1){
				dataSource.add("单选^-^ i%3 == 1"+i);
			}else{
				dataSource.add("单选^-^ "+i);
			}

		}
		mSizeTagAdapter.onlyAddAll(dataSource);
	}

	private void initListener() {
		//点击
		singleClickFlowLayout.setOnTagClickListener(new OnTagClickListener() {
			@Override
			public void onItemClick(FlowTagLayout parent, View view, int position) {
				VToast.info("点击了" + position);
			}
		});

		//单选
		singleChooseFlowLayout.setOnTagSelectListener(new OnTagSelectListener() {
			@Override
			public void onItemSelect(FlowTagLayout parent, int position, List<Integer> selectedList) {
				if (selectedList != null && selectedList.size() > 0) {
					StringBuilder sb = new StringBuilder();
					for (int i : selectedList) {
						sb.append(parent.getAdapter().getItem(i));
						sb.append(":");
					}
					Snackbar.make(parent, position + "恭喜你" + sb.toString(), Snackbar.LENGTH_LONG)
							.setAction("Action", null).show();
				} else {
					Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
							.setAction("Action", null).show();
				}
			}
		});

		//多选
		multiSelectFlowLayout.setOnTagSelectListener(new OnTagSelectListener() {
			@Override
			public void onItemSelect(FlowTagLayout parent, int positon, List<Integer> selectedList) {
				if (selectedList != null && selectedList.size() > 0) {
					StringBuilder sb = new StringBuilder();

					for (int i : selectedList) {
						sb.append(parent.getAdapter().getItem(i));
						sb.append(":");
					}
					Snackbar.make(parent, "O(∩_∩)O哈哈哈~:" + sb.toString(), Snackbar.LENGTH_LONG)
							.setAction("Action", null).show();
				} else {
					Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
							.setAction("Action", null).show();
				}
			}
		});
	}


}
