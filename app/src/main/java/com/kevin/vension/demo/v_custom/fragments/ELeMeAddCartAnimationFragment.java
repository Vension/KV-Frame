package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.adapters.FoodAdapter;
import com.kevin.vension.demo.v_custom.models.FoodModel;
import com.vension.customview.NXHooldeView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class ELeMeAddCartAnimationFragment extends BaseCustomViewFragment implements FoodAdapter.FoodActionCallback{

	@BindView(R.id.lv_main)
	ListView lvMain;
	@BindView(R.id.iv_goods_car_nums)
	ImageView ivGoodsCarNums;
	@BindView(R.id.tv_good_fitting_num)
	TextView tvGoodFittingNum;
	@BindView(R.id.rl_goods_fits_car)
	RelativeLayout rlGoodsFitsCar;
	@BindView(R.id.tv_goods_fitt_all_price)
	TextView tvGoodsFittAllPrice;
	@BindView(R.id.tv_goods_fitt_submit)
	TextView tvGoodsFittSubmit;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_eleme_addcart;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		lvMain.setAdapter(new FoodAdapter(getActivity(), factoryFoods(), this));
	}

	@Override
	public void lazyLoadData() {

	}

	public List<FoodModel> factoryFoods() {
		List<FoodModel> datas = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			FoodModel _FoodModel = new FoodModel();
			_FoodModel.setName("Kevin-Vension" + i);
			_FoodModel.setDescription("两侧和后部头发较" + i);
			_FoodModel.setPath(R.mipmap.ic_android);
			datas.add(_FoodModel);
		}
		return datas;
	}

	@Override
	public void addAction(View view) {
		NXHooldeView nxHooldeView = new NXHooldeView(getActivity());
		int position[] = new int[2];
		view.getLocationInWindow(position);
		nxHooldeView.setStartPosition(new Point(position[0], position[1]));
		ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
		rootView.addView(nxHooldeView);
		int endPosition[] = new int[2];
		tvGoodFittingNum.getLocationInWindow(endPosition);
		nxHooldeView.setEndPosition(new Point(endPosition[0], endPosition[1]));
		nxHooldeView.startBeizerAnimation();
	}

}

