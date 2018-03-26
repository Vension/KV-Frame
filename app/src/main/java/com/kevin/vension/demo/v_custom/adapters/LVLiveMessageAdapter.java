package com.kevin.vension.demo.v_custom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kevin.vension.demo.R;

import java.util.List;

/**
 * ilaty
 * date:2016-06-29
 * */
public class LVLiveMessageAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> list;

	public LVLiveMessageAdapter(Context mContext, List<String> list){
		this.mContext=mContext;
		this.list=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position % list.size());
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position % list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView= LayoutInflater.from(mContext).inflate(R.layout.item_test_lv_live, null);
			holder.item=(TextView) convertView.findViewById(R.id.item);

			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}

		holder.item.setText(list.get(position % list.size()));
		return convertView;
	}
	static class ViewHolder{
		TextView item;
	}
}
