package com.kevin.vension.demo.v_custom.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.models.SubjectsInfo;
import com.kevin.vension.demo.v_custom.utils.ZoomOutPageTransformer;
import com.sunfusheng.glideimageview.GlideImageLoader;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class GalleryViewPagerFragment extends BaseCustomViewFragment {

	@BindView(R.id.view_pager)
	ViewPager viewPager;

	private PageAdapter adapter;
	private ArrayList<SubjectsInfo> list = new ArrayList<>();

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_gallery_viewpager;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);

		for (int i = 0; i < 15; i++) {
			SubjectsInfo _SubjectsInfo = new SubjectsInfo();
			_SubjectsInfo.setId(i);
			_SubjectsInfo.setName("名字" + i);
			if (i % 15 == 0){
				_SubjectsInfo.setImgurl("http://www.5djiaren.com/uploads/2015-04/17-115301_29.jpg");
			}
			if (i % 15 == 1){
				_SubjectsInfo.setImgurl("http://img1.dzwww.com:8080/tupian_pl/20160106/32/4152697013403556460.jpg");
			}
			if (i % 15 == 2){
				_SubjectsInfo.setImgurl("http://photo.l99.com/bigger/21/1415193165405_4sg3ds.jpg");
			}
			if (i % 15 == 3){
				_SubjectsInfo.setImgurl("http://photo.l99.com/bigger/20/1415193157174_j2fa5b.jpg");
			}

			list.add(_SubjectsInfo);
		}

		adapter = new PageAdapter(getActivity(), list);
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(5);
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
	}

	@Override
	public void lazyLoadData() {

	}

	private class PageAdapter extends PagerAdapter {
		private ArrayList<SubjectsInfo> subjectsInfos;
		private Context context;

		public PageAdapter(Context context, ArrayList<SubjectsInfo> subjectsInfos) {
			this.context = context;
			this.subjectsInfos = subjectsInfos;
		}

		@Override
		public int getCount() {
//			return Integer.MAX_VALUE;
			return subjectsInfos.size();
		}


		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_test_gallery_viewpager, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.iv_movie);
			TextView title = (TextView) view.findViewById(R.id.tv_title);
			final SubjectsInfo subjectsInfo = subjectsInfos.get(position);
//			final SubjectsInfo subjectsInfo = subjectsInfos.get(position % subjectsInfos.size());
			title.setText(subjectsInfo.getName());
			title.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context,subjectsInfo.getName() + "=>" + position, Toast.LENGTH_LONG).show();
				}
			});
			GlideImageLoader.create(imageView).loadImage(subjectsInfo.getImgurl(), R.color.placeholder_color);
			container.addView(view);

			return view;
		}


		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			container.removeView(view);
		}
	}

}
