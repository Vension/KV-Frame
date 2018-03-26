package com.kevin.vension.demo.ui.adapters;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.models.TestEntity;
import com.vension.frame.utils.DefIconFactory;
import com.vension.frame.utils.VObsoleteUtil;
import com.vension.frame.utils.VStringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class RecyHotAdapter extends BaseQuickAdapter<TestEntity, BaseViewHolder> {

	@BindView(R.id.tv_index)
	TextView tvIndex;
	@BindView(R.id.tv_custom_view)
	TextView tvCustomView;
	@BindView(R.id.tv_custom_desc)
	TextView tvCustomDesc;
	@BindView(R.id.layout_custome)
	View _LayoutCoustom;
	@BindView(R.id.layout_desc)
	View _LayoutDesc;

	public RecyHotAdapter() {
		super(R.layout.item_recy_hot);
	}

	@Override
	protected void convert(BaseViewHolder helper, TestEntity item) {
		ButterKnife.bind(this, helper.itemView);
		helper.addOnClickListener(R.id.tv_custom_view);
		helper.addOnClickListener(R.id.layout_desc);
		if (item != null) {
			tvCustomView.setText(item.getTitle());
			tvCustomDesc.setText(item.getDesc());
			tvIndex.setText(String.valueOf(item.getId()));
			_LayoutCoustom.setBackgroundColor(VObsoleteUtil.getColor(DefIconFactory.provideColor()));
			_LayoutDesc.setVisibility(VStringUtil.isEmpty(item.getDesc()) ? View.GONE : View.VISIBLE);
		}
	}
}
