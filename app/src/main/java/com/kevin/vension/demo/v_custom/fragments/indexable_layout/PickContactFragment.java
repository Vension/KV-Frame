package com.kevin.vension.demo.v_custom.fragments.indexable_layout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.fragments.indexable_layout.contact.ContactAdapter;
import com.kevin.vension.demo.v_custom.fragments.indexable_layout.contact.ContactEntity;
import com.kevin.vension.demo.v_custom.fragments.indexable_layout.contact.MenuEntity;
import com.vension.customview.indexable_layout.IndexableAdapter;
import com.vension.customview.indexable_layout.IndexableHeaderAdapter;
import com.vension.customview.indexable_layout.IndexableLayout;
import com.vension.customview.indexable_layout.SimpleFooterAdapter;
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

public class PickContactFragment extends BaseCustomViewFragment {
	@BindView(R.id.indexableLayout)
	IndexableLayout indexableLayout;

	private ContactAdapter mAdapter;
	private MenuHeaderAdapter mMenuHeaderAdapter;
	private BannerHeaderAdapter mBannerHeaderAdapter;
	
	
	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_index_pick_contact;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		initData();
	}

	@Override
	public void lazyLoadData() {

	}

	private void initData() {
		// setAdapter
		mAdapter = new ContactAdapter(getActivity());
		indexableLayout.setAdapter(mAdapter);
		// set Datas
		mAdapter.setDatas(initDatas());
		// set Material Design OverlayView
		indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);

		// set Listener
		mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<ContactEntity>() {
			@Override
			public void onItemClick(View v, int originalPosition, int currentPosition, ContactEntity entity) {
				if (originalPosition >= 0) {
					VToastUtil.showToast( "选中:" + entity.getNick() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
				} else {
					VToastUtil.showToast( "选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);
				}
			}
		});

		mAdapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
			@Override
			public void onItemClick(View v, int currentPosition, String indexTitle) {
				VToastUtil.showToast( "选中:" + indexTitle + "  当前位置:" + currentPosition);
			}
		});

		// 添加我关心的人
		indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter<>(mAdapter, "☆", "我关心的", initFavDatas()));

		// 构造函数里3个参数,分别对应 (IndexBar的字母索引, IndexTitle, 数据源), 不想显示哪个就传null, 数据源传null时,代表add一个普通的View
		mMenuHeaderAdapter = new MenuHeaderAdapter("↑", null, initMenuDatas());
		// 添加菜单
		indexableLayout.addHeaderAdapter(mMenuHeaderAdapter);
		mMenuHeaderAdapter.setOnItemHeaderClickListener(new IndexableHeaderAdapter.OnItemHeaderClickListener<MenuEntity>() {
			@Override
			public void onItemClick(View v, int currentPosition, MenuEntity entity) {
				VToastUtil.showToast( entity.getMenuTitle());
			}
		});

		// 这里BannerView只有一个Item, 添加一个长度为1的任意List作为第三个参数
		List<String> bannerList = new ArrayList<>();
		bannerList.add("");
		mBannerHeaderAdapter = new BannerHeaderAdapter(null, null, bannerList);
		// 添加 Banner
		indexableLayout.addHeaderAdapter(mBannerHeaderAdapter);

		// FooterView
		indexableLayout.addFooterAdapter(new SimpleFooterAdapter<>(mAdapter, "尾", "我是FooterView", initFavDatas()));
	}



	/**
	 * 自定义的MenuHeader
	 */
	class MenuHeaderAdapter extends IndexableHeaderAdapter<MenuEntity> {
		private static final int TYPE = 1;

		public MenuHeaderAdapter(String index, String indexTitle, List<MenuEntity> datas) {
			super(index, indexTitle, datas);
		}

		@Override
		public int getItemViewType() {
			return TYPE;
		}

		@Override
		public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
			return new VH(LayoutInflater.from(getActivity()).inflate(R.layout.header_contact_menu, parent, false));
		}

		@Override
		public void onBindContentViewHolder(RecyclerView.ViewHolder holder, MenuEntity entity) {
			VH vh = (VH) holder;
			vh.tv.setText(entity.getMenuTitle());
			vh.img.setImageResource(entity.getMenuIconRes());
		}

		private class VH extends RecyclerView.ViewHolder {
			private TextView tv;
			private ImageView img;

			public VH(View itemView) {
				super(itemView);
				tv = (TextView) itemView.findViewById(R.id.tv_title);
				img = (ImageView) itemView.findViewById(R.id.img);
			}
		}
	}

	/**
	 * 自定义的Banner Header
	 */
	class BannerHeaderAdapter extends IndexableHeaderAdapter {
		private static final int TYPE = 2;

		public BannerHeaderAdapter(String index, String indexTitle, List datas) {
			super(index, indexTitle, datas);
		}

		@Override
		public int getItemViewType() {
			return TYPE;
		}

		@Override
		public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_contact_banner, parent, false);
			VH holder = new VH(view);
			holder.img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					VToastUtil.showToast( "---点击了Banner---");
				}
			});
			return holder;
		}

		@Override
		public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
			// 数据源为null时, 该方法不用实现
		}

		private class VH extends RecyclerView.ViewHolder {
			ImageView img;

			public VH(View itemView) {
				super(itemView);
				img = (ImageView) itemView.findViewById(R.id.img);
			}
		}
	}

	private List<ContactEntity> initDatas() {
		List<ContactEntity> list = new ArrayList<>();
		// 初始化数据
		List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.contact_array));
		List<String> mobileStrings = Arrays.asList(getResources().getStringArray(R.array.mobile_array));
		for (int i = 0; i < contactStrings.size(); i++) {
			ContactEntity contactEntity = new ContactEntity(contactStrings.get(i), mobileStrings.get(i));
			list.add(contactEntity);
		}
		return list;
	}

	private List<ContactEntity> initFavDatas() {
		List<ContactEntity> list = new ArrayList<>();
		list.add(new ContactEntity("张三", "10000"));
		list.add(new ContactEntity("李四", "10001"));
		return list;
	}

	private List<MenuEntity> initMenuDatas() {
		List<MenuEntity> list = new ArrayList<>();
		list.add(new MenuEntity("新的朋友", R.mipmap.ic_android));
		list.add(new MenuEntity("群聊", R.mipmap.ic_android));
		list.add(new MenuEntity("标签", R.mipmap.ic_android));
		list.add(new MenuEntity("公众号", R.mipmap.ic_android));
		return list;
	}
}
