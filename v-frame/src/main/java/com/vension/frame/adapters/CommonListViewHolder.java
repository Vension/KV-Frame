package com.vension.frame.adapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Vension on 2017/9/5.
 * ListView 和 GridView 的通用适配器的公共ViewHolder
 */

public class CommonListViewHolder {

	/**
	 * layout文件中的控件集合
	 * SparseArray用法与HashMap类似，性能比HashMap更优
	 */

	private SparseArray<View> mViews;

	/**
	 * BaseAdapter中的getView方法中对应的参数
	 */
	private View mConvertView;

	/**
	 * 私有，禁止外部实例化
	 * @param context
	 * @param parent BaseAdapter中的getView方法中对应的参数
	 * @param layoutId layout资源文件ID
	 */
	private CommonListViewHolder(Context context, ViewGroup parent, int layoutId){
		this.mViews = new SparseArray<View>();
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		this.mConvertView.setTag(this);
	}

	/**
	 *
	 * @param context
	 * @param convertView BaseAdapter中的getView方法中对应的参数
	 * @param parent BaseAdapter中的getView方法中对应的参数
	 * @param layoutId layout资源文件ID
	 * @return
	 */
	public static CommonListViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId){
		if(convertView == null){
			return new CommonListViewHolder(context, parent, layoutId);
		}
		return (CommonListViewHolder)convertView.getTag();
	}

	/**
	 * 根据ViewID获取控件对象，先从mViews集合中查找，
	 * 如果存在则直接返回该对象；
	 * 不存在则从布局文件中获取该对象，然后添加到mViews集合中，然后再返回该对象；
	 * @param viewid 控件ID
	 * @param <T>
	 * @return
	 */
	public <T extends View> T getView(int viewid){
		View view = mViews.get(viewid);
		if(view == null){
			view = mConvertView.findViewById(viewid);
			mViews.put(viewid, view);
		}
		return (T)view;
	}

	/**
	 * 返回BaseAdapter中的getView方法中对应的参数（convertView）
	 * @return
	 */
	public View getConvertView(){
		return mConvertView;
	}

	/**
	 * 获取TextView控件
	 * @param viewid 控件ID
	 * @return
	 */
	public TextView getTextView(int viewid){
		return (TextView)getView(viewid);
	}


	public CheckBox getCheckBox(int viewid){
		return (CheckBox)getView(viewid);
	}

	/**
	 * 获取Button控件
	 * @param viewid 控件ID
	 * @return
	 */
	public Button getButton(int viewid){
		return (Button)getView(viewid);
	}

	/**
	 * 获取ImageView控件
	 * @param viewid 控件ID
	 * @return
	 */
	public ImageView getImageView(int viewid){
		return (ImageView)getView(viewid);
	}

	/**
	 * 获取ImageButton控件
	 * @param viewid 控件ID
	 * @return
	 */
	public ImageButton getImageButton(int viewid){
		return (ImageButton)getView(viewid);
	}

	public RatingBar getRatingBar(int viewid){
		return (RatingBar)getView(viewid);
	}

	/**
	 * 设置TextView内容
	 * @param viewid TextView控件ID
	 * @param content 要设置的内容
	 * @return
	 */
	public CommonListViewHolder setText(int viewid, String content){
		getTextView(viewid).setText(content);
		return this;
	}

	public CommonListViewHolder setVisibility(int viewid, int visibility){
		getView(viewid).setVisibility(visibility);
		return this;
	}

	/**设置RatingBar*/
	public void setRating(int viewid, float rating) {
		getRatingBar(viewid).setRating(rating);
	}


	public CommonListViewHolder setCheck(int viewid, boolean isChecked){
		getCheckBox(viewid).setChecked(isChecked);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * @param viewid ImageView控件ID
	 * @param resid 要设置的图片资源ID
	 * @return
	 */
	public CommonListViewHolder setImageResource(int viewid, int resid){
		getImageView(viewid).setImageResource(resid);
		return this;
	}



}
