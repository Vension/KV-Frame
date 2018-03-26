package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.flipshare.FlipShareView;
import com.vension.customview.flipshare.ShareItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class FlipShareViewFragment extends BaseCustomViewFragment {
	@BindView(R.id.btn_left_top)
	Button btnLeftTop;
	@BindView(R.id.btn_middle_top)
	Button btnMiddleTop;
	@BindView(R.id.btn_right_top)
	Button btnRightTop;
	@BindView(R.id.btn_left_bottom)
	Button btnLeftBottom;
	@BindView(R.id.btn_middle_bottom)
	Button btnMiddleBottom;
	@BindView(R.id.btn_right_bottom)
	Button btnRightBottom;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_flipshare;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick({R.id.btn_left_top, R.id.btn_middle_top, R.id.btn_right_top,
			R.id.btn_left_bottom, R.id.btn_middle_bottom, R.id.btn_right_bottom})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_left_top:
				FlipShareView share = new FlipShareView.Builder(getActivity(), btnLeftTop)
						.addItem(new ShareItem("Facebook", Color.WHITE, 0xff43549C, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_home)))
						.addItem(new ShareItem("Twitter", Color.WHITE, 0xff4999F0, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_discover)))
						.addItem(new ShareItem("Google+", Color.WHITE, 0xffD9392D, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_me)))
						.addItem(new ShareItem("http://www.wangyuwei.me", Color.WHITE, 0xff57708A))
						.setBackgroundColor(0x60000000)
						.create();

				share.setOnFlipClickListener(new FlipShareView.OnFlipClickListener() {
					@Override
					public void onItemClick(int position) {
						Toast.makeText(getActivity(), "position " + position + " is clicked.", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void dismiss() {

					}
				});

				break;
			case R.id.btn_middle_top:
				new FlipShareView.Builder(getActivity(), btnMiddleTop)
						.addItem(new ShareItem("Facebook", Color.WHITE, 0xff43549C, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_home)))
						.addItem(new ShareItem("Wangyuwei", Color.WHITE, 0xff4999F0))
						.addItem(new ShareItem("Wangyuweiwangyuwei", Color.WHITE, 0xffD9392D))
						.addItem(new ShareItem("纯文字也可以", Color.WHITE, 0xff57708A))
						.setAnimType(FlipShareView.TYPE_HORIZONTAL)
						.create();
				break;
			case R.id.btn_right_top:
				new FlipShareView.Builder(getActivity(), btnRightTop)
						.addItem(new ShareItem("Facebook", Color.WHITE, 0xff43549C, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_home)))
						.addItem(new ShareItem("Twitter", Color.WHITE, 0xff4999F0, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_discover)))
						.addItem(new ShareItem("Google+", Color.WHITE, 0xffD9392D, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_me)))
						.addItem(new ShareItem("http://www.wangyuwei.me", Color.WHITE, 0xff57708A))
						.setItemDuration(500)
						.setBackgroundColor(0x60000000)
						.setAnimType(FlipShareView.TYPE_SLIDE)
						.create();
				break;
			case R.id.btn_left_bottom:
				new FlipShareView.Builder(getActivity(), btnLeftBottom)
						.addItem(new ShareItem("Facebook"))
						.addItem(new ShareItem("Twitter"))
						.addItem(new ShareItem("Google+"))
						.addItem(new ShareItem("http://www.wangyuwei.me", Color.WHITE, 0xff57708A))
						.addItem(new ShareItem("http://www.wangyuwei.me", Color.WHITE, 0xff57708A))
						.setSeparateLineColor(0x30000000)
						.setAnimType(FlipShareView.TYPE_HORIZONTAL)
						.create();
				break;
			case R.id.btn_middle_bottom:
				FlipShareView shareBottom = new FlipShareView.Builder(getActivity(), btnMiddleBottom)
						.addItem(new ShareItem("Facebook"))
						.addItem(new ShareItem("Twitter"))
						.addItem(new ShareItem("Google+"))
						.addItem(new ShareItem("http://www.wangyuwei.me", Color.WHITE, 0xff57708A))
						.setSeparateLineColor(0x30000000)
						.setBackgroundColor(0x60000000)
						.setAnimType(FlipShareView.TYPE_SLIDE)
						.create();
				shareBottom.setOnFlipClickListener(new FlipShareView.OnFlipClickListener() {
					@Override
					public void onItemClick(int position) {
						Toast.makeText(getActivity(), "position " + position + " is clicked.", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void dismiss() {

					}
				});
				break;
			case R.id.btn_right_bottom:
				new FlipShareView.Builder(getActivity(), btnRightBottom)
						.addItem(new ShareItem("Facebook", Color.WHITE, 0xff43549C, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_home)))
						.addItem(new ShareItem("Twitter", Color.WHITE, 0xff4999F0, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_discover)))
						.addItem(new ShareItem("Google+", Color.WHITE, 0xffD9392D, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab_me)))
						.addItem(new ShareItem("http://www.wangyuwei.me", Color.WHITE, 0xff57708A))
						.create();
				break;
		}
	}


}
