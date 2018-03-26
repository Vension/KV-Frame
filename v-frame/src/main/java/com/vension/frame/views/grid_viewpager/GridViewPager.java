package com.vension.frame.views.grid_viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.vension.frame.views.VGridView;
import com.vension.frame.views.VViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Created by vension on 2017/12/21.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 类型分类效果
 */

public class GridViewPager extends RelativeLayout {
	private Context mContext;
	private ViewPager mPager;
	private List<SortModel> mData;
	private List<GridView> mPagerList;

	private GridItemClickListener gridItemClickListener;
	private GridItemLongClickListener gridItemLongClickListener;

	/**
	 * 是否自定义指示器圆点
	 */
	private boolean hasCustomOval = false;

	/**
	 * 总的页数 计算得出
	 */
	private int pageCount;

	/**
	 * 每一页显示的个数 可设置
	 */
	private int pageSize = 10;

	/**
	 * GridView 每一行显示的个数
	 *
	 * 默认一行显示5个
	 */
	private int numColumn = 5;

	/**
	 * 当前显示的是第几页
	 */
	private int curIndex = 0;

	public GridViewPager(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public GridViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	public GridViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		initView();
	}

	private void initView() {
		//初始化ViewPager并添加到rootView
		mPager = new VViewPager(mContext);
		RelativeLayout.LayoutParams lp = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mPager.setLayoutParams(lp);
		addView(mPager, 0);
	}

	/**
	 * necessary 必须作为最后一步
	 *
	 * @param list
	 * @return
	 */
	public GridViewPager init(List<SortModel> list) {
		mData = list;
		//总的页数=总数/每页数量，并取整
		pageCount = (int) Math.ceil(mData.size() * 1.0 / pageSize);
		mPagerList = new ArrayList<GridView>();
		for (int i = 0; i < pageCount; i++) {
			//每个页面都是inflate出一个新实例
			VGridView gridView = new VGridView(mContext);
			gridView.setNumColumns(numColumn);
			gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
			gridView.setAdapter(new GridViewAdapter(mContext, mData, i, pageSize));
			mPagerList.add(gridView);

			gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
					if (gridItemClickListener == null) return;
					int position = pos + curIndex * pageSize;
					gridItemClickListener.click(pos, position, mData.get(position).getName());
				}
			});
			//true if the callback consumed the long click, false otherwise
			gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
					if (gridItemLongClickListener == null) return false;
					else {
						int position = pos + curIndex * pageSize;
						gridItemLongClickListener.click(pos, position, mData.get(position).getName());
						return true;
					}
				}
			});
		}
		//设置适配器
		mPager.setAdapter(new ViewPagerAdapter<GridView>(mPagerList));
		//设置圆点
		if (!hasCustomOval) setOvalLayout();
		mPager.invalidate();
		return this;
	}


	/**
	 * 设置圆点
	 */
	private void setOvalLayout() {
		mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public void onPageSelected(int position) {
				curIndex = position;
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	/**
	 * optional 设置单元点击事件
	 *
	 * @param listener
	 * @return
	 */
	public GridViewPager setGridItemClickListener(GridItemClickListener listener) {
		gridItemClickListener = listener;
		return this;
	}

	/**
	 * optional 设置单元长按事件
	 *
	 * @param listener
	 * @return
	 */
	public GridViewPager setGridItemLongClickListener(GridItemLongClickListener listener) {
		gridItemLongClickListener = listener;
		return this;
	}

	public List<GridView> getmPagerList() {
		return mPagerList;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public GridViewPager setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public int getCurIndex() {
		return curIndex;
	}

	public GridViewPager setGridNumColumns(int numColumn) {
		this.numColumn = numColumn;
		return this;
	}

	public ViewPager getViewPager() {
		return mPager;
	}
}
