package com.kevin.vension.demo.v_custom.fragments.stickynav_layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.stickynavlayout.SimpleViewPagerIndicator;

import org.jetbrains.annotations.Nullable;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class StickyNavFragment extends BaseCustomViewFragment {
	private String[] mTitles = new String[] { "简介", "评价", "相关" };
	private SimpleViewPagerIndicator mIndicator;
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private StickNavLayout_TabFragment[] mFragments = new StickNavLayout_TabFragment[mTitles.length];

	@Override
	public boolean showToolBar() {
		return false;
	}

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_sticky_nav_layout;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		initViews();
		initDatas();
		initEvents();
	}

	@Override
	public void lazyLoadData() {

	}

	private void initEvents() {
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
									   int positionOffsetPixels)
			{
				mIndicator.scroll(position, positionOffset);
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});

	}

	private void initDatas()
	{
		mIndicator.setTitles(mTitles);

		for (int i = 0; i < mTitles.length; i++)
		{
			mFragments[i] = StickNavLayout_TabFragment.newInstance(mTitles[i]);
		}

		mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager())
		{
			@Override
			public int getCount()
			{
				return mTitles.length;
			}

			@Override
			public Fragment getItem(int position)
			{
				return mFragments[position];
			}

		};

		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
	}

	private void initViews() {
		mIndicator = getRootView().findViewById(R.id.id_stickynavlayout_indicator);
		mViewPager = getRootView().findViewById(R.id.id_stickynavlayout_viewpager);
		/*
		RelativeLayout ll = (RelativeLayout) findViewById(R.id.id_stickynavlayout_topview);
		TextView tv = new TextView(this);
		tv.setText("我的动态添加的");
		tv.setBackgroundColor(0x77ff0000);
		ll.addView(tv, new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, 600));
		*/
	}
}
