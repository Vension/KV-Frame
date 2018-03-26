package com.kevin.vension.demo.v_custom.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.coverflow.FeatureCoverFlow;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class CoverFlowViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.coverflow)
	FeatureCoverFlow mCoverFlow;
	@BindView(R.id.title)
	TextSwitcher mTitle;

	private CoverFlowAdapter mAdapter;
	private ArrayList<GameEntity> mData = new ArrayList<>(0);

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_coverflowview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		init();
	}

	@Override
	public void lazyLoadData() {

	}

	private void init() {
		mData.add(new GameEntity(R.mipmap.image_1, R.string.title1));
		mData.add(new GameEntity(R.mipmap.image_2, R.string.title2));
		mData.add(new GameEntity(R.mipmap.image_3, R.string.title3));
		mData.add(new GameEntity(R.mipmap.image_4, R.string.title4));

		mTitle.setFactory(new ViewSwitcher.ViewFactory() {
			@Override
			public View makeView() {
				TextView textView = new TextView(getActivity());
				return textView;
			}
		});
		Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_top);
		Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom);
		mTitle.setInAnimation(in);
		mTitle.setOutAnimation(out);

		mAdapter = new CoverFlowAdapter(getActivity());
		mAdapter.setData(mData);
		mCoverFlow.setAdapter(mAdapter);

		mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getActivity(),
						getResources().getString(mData.get(position).titleResId),
						Toast.LENGTH_SHORT).show();
			}
		});

		mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
			@Override
			public void onScrolledToPosition(int position) {
				mTitle.setText(getResources().getString(mData.get(position).titleResId));
			}

			@Override
			public void onScrolling() {
				mTitle.setText("");
			}
		});
	}


	private class CoverFlowAdapter extends BaseAdapter {

		private ArrayList<GameEntity> mData = new ArrayList<>(0);
		private Context mContext;

		public CoverFlowAdapter(Context context) {
			mContext = context;
		}

		public void setData(ArrayList<GameEntity> data) {
			mData = data;
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int pos) {
			return mData.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View rowView = convertView;

			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.item_coverflow, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.text = (TextView) rowView.findViewById(R.id.label);
				viewHolder.image = (ImageView) rowView
						.findViewById(R.id.image);
				rowView.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();

			holder.image.setImageResource(mData.get(position).imageResId);
			holder.text.setText(mData.get(position).titleResId);

			return rowView;
		}


		private class ViewHolder {
			public TextView text;
			public ImageView image;
		}
	}


	public class GameEntity {
		public int imageResId;
		public int titleResId;

		public GameEntity (int imageResId, int titleResId){
			this.imageResId = imageResId;
			this.titleResId = titleResId;
		}
	}


}
