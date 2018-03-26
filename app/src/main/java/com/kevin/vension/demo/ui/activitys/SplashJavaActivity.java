package com.kevin.vension.demo.ui.activitys;

import android.animation.Animator;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.base.BaseActivity;
import com.kevin.vension.demo.utils.AppCompat;
import com.vension.frame.VFrame;
import com.vension.frame.utils.AppUtil;
import com.vension.frame.utils.TimeUtil;
import com.vension.frame.views.CountDownCircleView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2018/2/28.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class SplashJavaActivity extends BaseActivity {

	@BindView(R.id.count_down_view)
	CountDownCircleView countDownView;
	@BindView(R.id.iv_splash)
	ImageView ivSplash;
	@BindView(R.id.tv_version)
	TextView tvVersion;

	@Override
	public boolean isEnableSlideClose() {
		return false;
	}

	@Override
	public boolean isCheckNet() {
		return true;
	}

	@Override
	public int attachLayoutRes() {
		return R.layout.activity_splash_java;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		String welcome_hint = getString(R.string.welcome_hint, AppUtil.Companion.getVersionName(VFrame.getContext()),"2014", TimeUtil.getNowString("yyyy"),"kevin~vension.com");
		tvVersion.setText(welcome_hint);
//		doInit();
		startAnim();
	}

	@Override
	public void requestLoadData() {
	}

	private void startAnim() {
		ivSplash.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(500).setListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animator) {

			}

			@Override
			public void onAnimationEnd(Animator animator) {
				showAdvertisement();//显示广告页
			}

			@Override
			public void onAnimationCancel(Animator animator) {

			}

			@Override
			public void onAnimationRepeat(Animator animator) {

			}
		}).start();
	}


	private void showAdvertisement() {
		ivSplash.setImageResource(R.mipmap.img_advertisment);
		startcountDown();
	}


	/**
	 *进行一些初始化操作
	 */
//	private void doInit() {
//		if (NetworkUtil.isNetworkAvailable(_Activity)) {
//			//	网络已连接
//			if (UserACache.getInstance().hasInit()) {
//				//有网络则判断token是否失效，失效则获取
//				getAccessToken();
//			}else{
//				startcountDown();
//			}
//		}else{
//			//无网络直接进入
//			ToastUtil.warning(SplashJavaActivity.this,"请检查您的网络连接");
//			startcountDown();
//		}
//	}


	/**
	 * 获取Token
	 */
//	private void getAccessToken() {
//		RetrofitFactory.getInstance().getToken(new HttpCallBack<TokenEntity>() {
//
//			@Override
//			protected void onFailure(String errorMessage) {
//				ToastUtil.error(_Activity,errorMessage);
//			}
//
//			@Override
//			protected void onFinish() {
//				LogUtil.i("onFinish");
//			}
//
//			@Override
//			protected void onSuccess(TokenEntity tokenEntity) {
//				UserACache.getInstance().setToken(tokenEntity);
//				//在这里添加一些图片加载完成的操作
//				startcountDown();
//			}
//		});
//	}

	/**开始倒计时*/
	private long mcountDownTime = 3 * 1000;
	private void startcountDown() {
		countDownView.setVisibility(View.VISIBLE);
		countDownView.setCountdownTime(mcountDownTime);
		countDownView.startCountDownTime(new CountDownCircleView.OnCountdownFinishListener() {
			@Override
			public void countdownFinished() {
				startMainActivity();
			}
		});
	}


	/**
	 * 进入主界面
	 */
	private void startMainActivity() {
//		if (UserACache.getInstance().isLogin()) {
		AppCompat.startMainActivity(this);
//		} else {
		//登录
//			startAgentActivity(LoginFragment.class);
//		}
		finish();
	}



	@OnClick({R.id.count_down_view})
	public void onClick(){
		//跳过
		startMainActivity();
	}


	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
