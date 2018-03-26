package com.kevin.vension.demo.ui.adapters;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.models.TestEntity;
import com.sunfusheng.glideimageview.GlideImageView;
import com.vension.frame.utils.DefIconFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class RecyHomeAdapter extends BaseQuickAdapter<TestEntity, BaseViewHolder> {

	@BindView(R.id.giv_product)
	GlideImageView givProduct;
	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.tv_desc)
	TextView tvDesc;
	@BindView(R.id.tv_date)
	TextView tvDate;

	public RecyHomeAdapter() {
		super(R.layout.item_recy_home);
	}

	@Override
	protected void convert(BaseViewHolder helper, TestEntity item) {
		ButterKnife.bind(this, helper.itemView);
		givProduct.loadLocalImage(DefIconFactory.provideIcon(),R.color.placeholder_color);
		tvTitle.setText(item.getTitle());
		tvDesc.setText(item.getDesc());
		tvDate.setText(item.getDate());
	}

}
