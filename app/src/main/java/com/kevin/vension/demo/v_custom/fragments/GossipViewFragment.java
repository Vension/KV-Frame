package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.GossipView;
import com.vension.frame.utils.VToastUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class GossipViewFragment extends BaseCustomViewFragment {

	@BindView(R.id.gossipview)
	GossipView gossipview;


	String[] strs = {"安卓","微软","苹果","谷歌","百度","腾讯"} ;


	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_gossipview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		final List<GossipView.GossipItem> items =new ArrayList<GossipView.GossipItem>();
		for(int i = 0; i < strs.length; i++) {
			GossipView.GossipItem item = new GossipView.GossipItem(strs[i],3);
			items.add(item);
		}
		gossipview.setItems(items);
		gossipview.setNumber(3);
		gossipview.setOnPieceClickListener( new GossipView.OnPieceClickListener(){
			@Override
			public void onPieceClick(int index) {
				if(index != -1 &&  index != -2) {
					VToastUtil.showToast("你选择了" + items.get(index).getTitle());
				}
			}
		});
	}

	@Override
	public void lazyLoadData() {

	}
}
