package com.kevin.vension.demo.ui.adapters;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kevin.vension.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ：Created by vension on 2018/3/14.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class RecyKnowledgeSummaryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

	@BindView(R.id.tv_title)
	TextView tvTitle;

	public RecyKnowledgeSummaryAdapter() {
		super(R.layout.item_recy_knowledge);
	}

	@Override
	protected void convert(BaseViewHolder helper, String item) {
		ButterKnife.bind(this, helper.itemView);
		tvTitle.setText(item);
	}

}
