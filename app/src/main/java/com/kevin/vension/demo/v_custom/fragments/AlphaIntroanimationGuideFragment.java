package com.kevin.vension.demo.v_custom.fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.frame.utils.ColorUtil;
import com.vension.frame.utils.VToastUtil;
import com.vension.frame.views.flypage_indicaor.FlycoPageIndicaor;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class AlphaIntroanimationGuideFragment extends BaseCustomViewFragment {
	@BindView(R.id.pager)
	ViewPager viewPager;
	@BindView(R.id.indicator_circle_stroke)
	FlycoPageIndicaor mIndicator;
	@BindView(R.id.landing_backgrond)
	RelativeLayout landingBGView;

	private static final String SAVING_STATE_SLIDER_ANIMATION = "SliderAnimationSavingState";
	private boolean isSliderAnimation = false;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_alpha_introanimation_guide;
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
		viewPager.setAdapter(new ViewPagerAdapter(R.array.icons, R.array.titles, R.array.hints));
		mIndicator.setViewPager(viewPager);

		viewPager.setPageTransformer(true, new CustomPageTransformer());

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

				int colorBg[] = getResources().getIntArray(R.array.landing_bg);


				ColorUtil shades = new ColorUtil();
				shades.setFromColor(colorBg[position % colorBg.length])
						.setToColor(colorBg[(position + 1) % colorBg.length])
						.setShade(positionOffset);

				landingBGView.setBackgroundColor(shades.generate());

			}

			public void onPageSelected(int position) {
				if (position == 3){
					VToastUtil.showToast("进入主页");
//					Intent intent = new Intent();
//					intent.setClass(getActivity(), SplashKotlinActivity.class);
//					startActivity(intent);
//					getActivity().finish();
//					getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				}
			}

			public void onPageScrollStateChanged(int state) {
			}
		});
	}



	public class ViewPagerAdapter extends PagerAdapter {

		private int iconResId, titleArrayResId, hintArrayResId;

		public ViewPagerAdapter(int iconResId, int titleArrayResId, int hintArrayResId) {

			this.iconResId = iconResId;
			this.titleArrayResId = titleArrayResId;
			this.hintArrayResId = hintArrayResId;
		}

		@Override
		public int getCount() {
			return getResources().getIntArray(iconResId).length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			Drawable icon = getResources().obtainTypedArray(iconResId).getDrawable(position);
			String title = getResources().getStringArray(titleArrayResId)[position];
			String hint = getResources().getStringArray(hintArrayResId)[position];

			View itemView = getActivity().getLayoutInflater().inflate(R.layout.viewpager_test_alpha_introanimation, container, false);
			ImageView iconView = (ImageView) itemView.findViewById(R.id.landing_img_slide);
			TextView titleView = (TextView) itemView.findViewById(R.id.landing_txt_title);
			TextView hintView = (TextView) itemView.findViewById(R.id.landing_txt_hint);

			iconView.setImageDrawable(icon);
			titleView.setText(title);
			hintView.setText(hint);

			container.addView(itemView);

			return itemView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((RelativeLayout) object);

		}
	}

	public class CustomPageTransformer implements ViewPager.PageTransformer {
		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();

			View imageView = view.findViewById(R.id.landing_img_slide);
			View contentView = view.findViewById(R.id.landing_txt_hint);
			View txt_title = view.findViewById(R.id.landing_txt_title);

			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left
			} else if (position <= 0) { // [-1,0]
				// This page is moving out to the left

				// Counteract the default swipe
				setTranslationX(view, pageWidth * -position);
				if (contentView != null) {
					// But swipe the contentView
					setTranslationX(contentView, pageWidth * position);
					setTranslationX(txt_title, pageWidth * position);

					setAlpha(contentView, 1 + position);
					setAlpha(txt_title, 1 + position);
				}

				if (imageView != null) {
					// Fade the image in
					setAlpha(imageView, 1 + position);
				}

			} else if (position <= 1) { // (0,1]
				// This page is moving in from the right

				// Counteract the default swipe
				setTranslationX(view, pageWidth * -position);
				if (contentView != null) {
					// But swipe the contentView
					setTranslationX(contentView, pageWidth * position);
					setTranslationX(txt_title, pageWidth * position);

					setAlpha(contentView, 1 - position);
					setAlpha(txt_title, 1 - position);

				}
				if (imageView != null) {
					// Fade the image out
					setAlpha(imageView, 1 - position);
				}

			}
		}
	}

	/**
	 * Sets the alpha for the view. The alpha will be applied only if the running android device OS is greater than honeycomb.
	 *
	 * @param view  - view to which alpha to be applied.
	 * @param alpha - alpha value.
	 */
	private void setAlpha(View view, float alpha) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && !isSliderAnimation) {
			view.setAlpha(alpha);
		}
	}

	/**
	 * Sets the translationX for the view. The translation value will be applied only if the running android device OS is greater than honeycomb.
	 *
	 * @param view         - view to which alpha to be applied.
	 * @param translationX - translationX value.
	 */
	private void setTranslationX(View view, float translationX) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && !isSliderAnimation) {
			view.setTranslationX(translationX);
		}
	}

	public void onSaveInstanceState(Bundle outstate) {
		if (outstate != null) {
			outstate.putBoolean(SAVING_STATE_SLIDER_ANIMATION, isSliderAnimation);
		}
		super.onSaveInstanceState(outstate);
	}


}
