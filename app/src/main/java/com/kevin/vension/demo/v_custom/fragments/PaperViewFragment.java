package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.paperview.PaperView;
import com.vension.customview.tagview.TagView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/27.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PaperViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.paperView)
	PaperView paperView;
	@BindView(R.id.text_data)
	TextView textData;
	@BindView(R.id.note_title)
	TextView noteTitle;
	@BindView(R.id.note_time)
	TextView noteTime;
	@BindView(R.id.note_content)
	TextView noteContent;
	@BindView(R.id.tag)
	TagView tag;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_paperview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);

		//设置随机颜色
		paperView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.gray));
		tag.setTagMaskColor(ContextCompat.getColor(getActivity(), R.color.gray));
	}

	@Override
	public void lazyLoadData() {

	}
}
