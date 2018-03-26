package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.vension.customview.likeviews.goodview.GoodView;
import com.vension.customview.likeviews.likebtn.LikeButton;
import com.vension.customview.likeviews.likebtn.OnLikeListener;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class LikeViewFragment extends BaseCustomViewFragment implements OnLikeListener {
	@BindView(R.id.star_button)
	LikeButton starButton;
	@BindView(R.id.heart_button)
	LikeButton heartButton;
	@BindView(R.id.thumb_button)
	LikeButton thumbButton;
	@BindView(R.id.smile_button)
	LikeButton smileButton;
	@BindView(R.id.vector_android_button)
	LikeButton vectorAndroidButton;

	GoodView mGoodView;
	@BindView(R.id.good)
	ImageView good;
	@BindView(R.id.good2)
	ImageView good2;
	@BindView(R.id.collection)
	ImageView collection;
	@BindView(R.id.bookmark)
	ImageView bookmark;
	@BindView(R.id.reset)
	Button reset;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_likeview;
	}


	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		initLikeBotton();
		usingCustomIcons();
	}

	@Override
	public void lazyLoadData() {

	}

	private void initLikeBotton() {
		starButton.setOnLikeListener(this);
		heartButton.setOnLikeListener(this);
		smileButton.setOnLikeListener(this);
		thumbButton.setOnLikeListener(this);
		thumbButton.setLiked(true);
	}

	public void usingCustomIcons() {
		mGoodView = new GoodView(getActivity());
		//shown when the button is in its default state or when unLiked.
		smileButton.setUnlikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(getActivity(), CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.darker_gray).sizeDp(25).toBitmap()));
		//shown when the button is liked!
		smileButton.setLikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(getActivity(), CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.holo_purple).sizeDp(25).toBitmap()));
	}

	@Override
	public void liked(LikeButton likeButton) {
		Toast.makeText(getActivity(), "Liked!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void unLiked(LikeButton likeButton) {
		Toast.makeText(getActivity(), "unLiked!", Toast.LENGTH_SHORT).show();
	}



	@OnClick({R.id.good, R.id.good2, R.id.collection, R.id.bookmark, R.id.reset})
	public void onClick(View view){
		switch (view.getId()){
			case R.id.good:
				good(view);
				break;
			case R.id.good2:
				good2(view);
				break;
			case R.id.collection:
				collection(view);
				break;
			case R.id.bookmark:
				bookmark(view);
				break;
			case R.id.reset:
				reset();
				break;
		}
	}

	public void good(View view) {
		((ImageView) view).setImageResource(R.mipmap.good_checked);
		mGoodView.setText("+1");
		mGoodView.show(view);
	}

	public void good2(View view) {
		((ImageView) view).setImageResource(R.mipmap.good_checked);
		mGoodView.setImage(getResources().getDrawable(R.mipmap.good_checked));
		mGoodView.show(view);
	}

	public void collection(View view) {
		((ImageView) view).setImageResource(R.mipmap.collection_checked);
		mGoodView.setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
		mGoodView.show(view);
	}

	public void bookmark(View view) {
		((ImageView) view).setImageResource(R.mipmap.bookmark_checked);
		mGoodView.setTextInfo("收藏成功", Color.parseColor("#ff941A"), 12);
		mGoodView.show(view);
	}

	public void reset() {
		good.setImageResource(R.mipmap.good);
		good2.setImageResource(R.mipmap.good);
		collection.setImageResource(R.mipmap.collection);
		bookmark.setImageResource(R.mipmap.bookmark);
		mGoodView.reset();
	}


}
