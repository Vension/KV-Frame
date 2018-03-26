package com.kevin.vension.demo.v_custom.fragments.alphaview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.alphaView.AlphaIndicator;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/27.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class AlphaViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.viewPager)
	ViewPager viewPager;
	@BindView(R.id.alphaIndicator)
	AlphaIndicator alphaIndicator;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_alphaview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);

		viewPager.setAdapter(new MainAdapter(getActivity().getSupportFragmentManager()));
		alphaIndicator.setViewPager(viewPager);
	}

	@Override
	public void lazyLoadData() {

	}

	private class MainAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments = new ArrayList<>();
		private String[] titles = {//
				"第一页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
				"第二页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
				"第三页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度", //
				"第四页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度"};

		public MainAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(AlphaTextFragment.newInstance(titles[0]));
			fragments.add(AlphaTextFragment.newInstance(titles[1]));
			fragments.add(AlphaTextFragment.newInstance(titles[2]));
			fragments.add(AlphaTextFragment.newInstance(titles[3]));
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
	}

}
