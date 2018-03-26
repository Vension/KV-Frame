package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.adapters.TextTagsAdapter;
import com.kevin.vension.demo.v_custom.adapters.VectorTagsAdapter;
import com.kevin.vension.demo.v_custom.adapters.ViewTagsAdapter;
import com.vension.customview.tag_3d.TagCloudView;

import org.jetbrains.annotations.Nullable;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class Tag_3DFragment extends BaseCustomViewFragment {
	private TagCloudView tagCloudView;
	private TextTagsAdapter textTagsAdapter;
	private ViewTagsAdapter viewTagsAdapter;
	private VectorTagsAdapter vectorTagsAdapter;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test__3d_tag;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);

		tagCloudView = getRootView().findViewById(R.id.tag_cloud);
		tagCloudView.setBackgroundColor(Color.LTGRAY);

		textTagsAdapter = new TextTagsAdapter(new String[20]);
		viewTagsAdapter = new ViewTagsAdapter();
		vectorTagsAdapter = new VectorTagsAdapter();

		tagCloudView.setAdapter(textTagsAdapter);

		getRootView().findViewById(R.id.tag_text).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tagCloudView.setAdapter(textTagsAdapter);
			}
		});

		getRootView().findViewById(R.id.tag_view).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tagCloudView.setAdapter(viewTagsAdapter);
			}
		});

		getRootView().findViewById(R.id.tag_vector).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tagCloudView.setAdapter(vectorTagsAdapter);
			}
		});

	}

	@Override
	public void lazyLoadData() {

	}
}
