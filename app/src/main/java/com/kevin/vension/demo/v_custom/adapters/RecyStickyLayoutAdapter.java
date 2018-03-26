package com.kevin.vension.demo.v_custom.adapters;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.models.TestEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ：Created by vension on 2017/12/21.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class RecyStickyLayoutAdapter extends BaseQuickAdapter<TestEntity, BaseViewHolder> {

	@BindView(R.id.id_info)
	TextView id_info;

	public RecyStickyLayoutAdapter(List<TestEntity> mDatas) {
		super(R.layout.item_sticky_tab,mDatas);
	}

	@Override
	protected void convert(BaseViewHolder helper, TestEntity item) {
		ButterKnife.bind(this, helper.itemView);
		id_info.setText(item.getTitle());
	}

}
