package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.recy_fastscroll.FastScrollRecyclerView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class FastScrollRecyclerViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.recycler)
	FastScrollRecyclerView recyclerView;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_recy_fastscroll;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(new RecyclerAdapter());
	}

	@Override
	public void lazyLoadData() {

	}

	private static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
			implements FastScrollRecyclerView.SectionedAdapter {

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_recy_fastscroll, parent, false));
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			holder.text.setText(String.format("Item %d", position));
		}

		@Override
		public int getItemCount() {
			return 100;
		}

		@NonNull
		@Override
		public String getSectionName(int position) {
			return String.valueOf(position);
		}

		static class ViewHolder extends RecyclerView.ViewHolder {
			public TextView text;

			public ViewHolder(View itemView) {
				super(itemView);
				text = (TextView) itemView.findViewById(R.id.text);
			}
		}
	}



}
