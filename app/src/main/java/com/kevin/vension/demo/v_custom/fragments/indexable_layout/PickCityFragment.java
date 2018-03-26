package com.kevin.vension.demo.v_custom.fragments.indexable_layout;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.fragments.indexable_layout.city.CityAdapter;
import com.kevin.vension.demo.v_custom.fragments.indexable_layout.city.CityEntity;
import com.kevin.vension.demo.v_custom.fragments.indexable_layout.city.SearchFragment;
import com.vension.customview.indexable_layout.IndexableAdapter;
import com.vension.customview.indexable_layout.IndexableLayout;
import com.vension.customview.indexable_layout.SimpleHeaderAdapter;
import com.vension.frame.utils.VToastUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PickCityFragment extends BaseCustomViewFragment {

	@BindView(R.id.searchview)
	SearchView mSearchView;
	@BindView(R.id.indexableLayout)
	IndexableLayout indexableLayout;

	@BindView(R.id.progress)
	FrameLayout mProgressBar;

	private SearchFragment mSearchFragment;
	private List<CityEntity> mDatas;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_index_city;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mSearchFragment = (SearchFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.search_fragment);
		// setAdapter
		CityAdapter adapter = new CityAdapter(getActivity());
		indexableLayout.setAdapter(adapter);
		// set Datas
		mDatas = initDatas();

		// 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。  按需开启～
//        indexableLayout.setFastCompare(true);

		adapter.setDatas(mDatas, new IndexableAdapter.IndexCallback<CityEntity>() {
			@Override
			public void onFinished(List<CityEntity> datas) {
				// 数据处理完成后回调
				mSearchFragment.bindDatas(mDatas);
				mProgressBar.setVisibility(View.GONE);
			}
		});

		// set Listener
		adapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CityEntity>() {
			@Override
			public void onItemClick(View v, int originalPosition, int currentPosition, CityEntity entity) {
				if (originalPosition >= 0) {
					VToastUtil.showToast( "选中:" + entity.getName() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
				} else {
					VToastUtil.showToast("选中Header:" + entity.getName() + "  当前位置:" + currentPosition);
				}
			}
		});

		adapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
			@Override
			public void onItemClick(View v, int currentPosition, String indexTitle) {
				VToastUtil.showToast("选中:" + indexTitle + "  当前位置:" + currentPosition);
			}
		});

		// 添加 HeaderView DefaultHeaderAdapter接收一个IndexableAdapter, 使其布局以及点击事件和IndexableAdapter一致
		// 如果想自定义布局,点击事件, 可传入 new IndexableHeaderAdapter

		// 热门城市
		indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter<>(adapter, "热", "热门城市", iniyHotCityDatas()));
		// 定位
		final List<CityEntity> gpsCity = iniyGPSCityDatas();
		final SimpleHeaderAdapter gpsHeaderAdapter = new SimpleHeaderAdapter<>(adapter, "定", "当前城市", gpsCity);
		indexableLayout.addHeaderAdapter(gpsHeaderAdapter);

		// 模拟定位
		indexableLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				gpsCity.get(0).setName("杭州市");
				gpsHeaderAdapter.notifyDataSetChanged();
			}
		}, 3000);

		// 搜索Demo
		initSearch();
	}

	@Override
	public void lazyLoadData() {

	}

	private List<CityEntity> initDatas() {
		List<CityEntity> list = new ArrayList<>();
		List<String> cityStrings = Arrays.asList(getResources().getStringArray(R.array.city_array));
		for (String item : cityStrings) {
			CityEntity cityEntity = new CityEntity();
			cityEntity.setName(item);
			list.add(cityEntity);
		}
		return list;
	}

	private List<CityEntity> iniyHotCityDatas() {
		List<CityEntity> list = new ArrayList<>();
		list.add(new CityEntity("杭州市"));
		list.add(new CityEntity("北京市"));
		list.add(new CityEntity("上海市"));
		list.add(new CityEntity("广州市"));
		return list;
	}

	private List<CityEntity> iniyGPSCityDatas() {
		List<CityEntity> list = new ArrayList<>();
		list.add(new CityEntity("定位中..."));
		return list;
	}

	private void initSearch() {
		getActivity().getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();

		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (newText.trim().length() > 0) {
					if (mSearchFragment.isHidden()) {
						getActivity().getSupportFragmentManager().beginTransaction().show(mSearchFragment).commit();
					}
				} else {
					if (!mSearchFragment.isHidden()) {
						getActivity().getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
					}
				}

				mSearchFragment.bindQueryText(newText);
				return false;
			}
		});
	}


	@Override
	public void onDestroy() {
		if (!mSearchFragment.isHidden()) {
			// 隐藏 搜索
			mSearchView.setQuery(null, false);
			getActivity().getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
			return;
		}
		super.onDestroy();
	}


}
