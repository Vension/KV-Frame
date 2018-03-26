package com.vension.frame.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/9/15.
 */

public class V_FragmentPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments = new ArrayList<>();     //fragment列表
	private List<String> titles = new ArrayList<>();          //fragment列表

	public V_FragmentPagerAdapter(FragmentManager manager) {
		super(manager);
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	//此方法用来显示tab上的名字
	@Override
	public CharSequence getPageTitle(int position) {
		if (titles != null && titles.size() > 0){
			return titles.get(position % titles.size());
		}
		return null;
	}

	public void addFragment(Fragment fragment) {
		fragments.add(fragment);
	}
	public void addTitle(String str) {titles.add(str);}

}
