package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.adapters.ProductsAdapter;
import com.kevin.vension.demo.v_custom.adapters.SaleAdapter;
import com.kevin.vension.demo.v_custom.models.SaleEntity;
import com.vension.customview.bulletinview.BulletinView;
import com.vension.customview.bulletinview.SimpleBulletinAdapter;
import com.vension.frame.utils.VToast;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2018/1/4.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class BulletinViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.bulletin_view)
	BulletinView mBulletinView;
	@BindView(R.id.bulletin_view_sale)
	BulletinView mBulletinViewSale;
	@BindView(R.id.bulletin_view_product)
	BulletinView mBulletinViewProduct;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_bulletinview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		initData();
	}

	@Override
	public void lazyLoadData() {

	}

	private void initData() {
		///////////////////////////////
		// 普通公告
		///////////////////////////////
		List<String> list = new ArrayList<>();
		list.add("智能数码手表12期免息！");
		list.add("领券家电立减800");
		mBulletinView.setAdapter(new SimpleBulletinAdapter(getContext(), list));

		mBulletinView.setOnBulletinItemClickListener(new BulletinView.OnBulletinItemClickListener() {
			@Override
			public void onBulletinItemClick(int position) {
				VToast.error("click:" + position);
			}
		});

		///////////////////////////////
		// 复杂公告
		///////////////////////////////
		final List<SaleEntity> saleEntities = new ArrayList<>();

		SaleEntity saleEntity = new SaleEntity();
		saleEntity.saleTitle = "花少爷的粥全场优惠券";
		saleEntity.salePrice = "10元";
		saleEntity.saleTag = "新人专享";
		saleEntity.saleImgRes = R.mipmap.sale0;

		SaleEntity saleEntity1 = new SaleEntity();
		saleEntity1.saleTitle = "豪客来全场代金券";
		saleEntity1.salePrice = "16元";
		saleEntity1.saleTag = "再减8元";
		saleEntity1.saleImgRes = R.mipmap.sale0;

		saleEntities.add(saleEntity);
		saleEntities.add(saleEntity1);

		mBulletinViewSale.setAdapter(new SaleAdapter(getContext(), saleEntities));
		mBulletinViewSale.setOnBulletinItemClickListener(new BulletinView.OnBulletinItemClickListener() {
			@Override
			public void onBulletinItemClick(int position) {
				VToast.warning( saleEntities.get(position).saleTitle);
			}
		});

		///////////////////////////////
		// 商品展示
		///////////////////////////////
		mBulletinViewProduct.setAdapter(new ProductsAdapter(getContext(), null));
	}



}
