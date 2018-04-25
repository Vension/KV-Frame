package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.PileLayout;
import com.vension.frame.utils.VToastUtil;
import com.vension.frame.views.VCircleImageView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/25 9:26
 * 描  述：
 * ========================================================
 */

public class PileLayoutFragment extends BaseCustomViewFragment {
	String[] urls = {
			"http://img2.imgtn.bdimg.com/it/u=1939271907,257307689&fm=21&gp=0.jpg",
			"http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg",
			"http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg",
			"http://img2.imgtn.bdimg.com/it/u=1939271907,257307689&fm=21&gp=0.jpg",
			"http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg",
			"http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg",

			"http://img2.imgtn.bdimg.com/it/u=1939271907,257307689&fm=21&gp=0.jpg",
			"http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg",
			"http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg",
			"http://img2.imgtn.bdimg.com/it/u=1939271907,257307689&fm=21&gp=0.jpg",
			"http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg",
			"http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg"
	};
	@BindView(R.id.pile_layout)
	PileLayout pileLayout;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_pilelayout;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		for (int i = 0; i < urls.length; i++) {
			VCircleImageView mCircleImageView = (VCircleImageView) inflater.inflate(R.layout.item_praise, pileLayout, false);
			Glide.with(this).load(urls[i]).into(mCircleImageView);
			mCircleImageView.setTag((i + 1));
			mCircleImageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					VToastUtil.showToast("点击了第" + view.getTag() + "张图片");
				}
			});
			pileLayout.addView(mCircleImageView);
		}
	}


	@Override
	public void lazyLoadData() {

	}


}
