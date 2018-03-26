package com.vension.frame.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Created by vension on 2018/1/10.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * ListView 和 GridView 的通用适配器
 */

public abstract class CommonListAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> listDatas = new ArrayList<>();
	protected int mLayoutId;

	public CommonListAdapter(Context context, List<T> data, int layoutId){
		this.mContext = context;
		this.listDatas = data;
		this.mLayoutId = layoutId;
	}

	@Override
	public int getCount() {
		return listDatas.size();
	}

	@Override
	public T getItem(int position) {
		return listDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 添加单条数据项
	 * @param item
	 */
	public void addItem(T item){
		this.listDatas.add(item);
	}

	/**
	 * 设置数据源
	 * @param data
	 */
	public void setListDatas(List<T> data){
		listDatas.clear();
		listDatas.addAll(data);
		refresh();
	}

	/**
	 * 清除数据源
	 */
	public void clear(){
		this.listDatas.clear();
	}

	/**
	 * 获取数据源
	 */
	public List<T> getDatas() {
		return listDatas;
	}

	/**
	 * 刷新数据源
	 */
	public void refresh(){
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommonListViewHolder holder = CommonListViewHolder.get(mContext, convertView, parent, mLayoutId);
		convert(holder, position, listDatas.get(position));
		return holder.getConvertView();
	}

	/**
	 * 在子类中实现该方法
	 * @param holder 列表项
	 * @param item
	 */
	public abstract void convert(CommonListViewHolder holder, int position, T item);


}