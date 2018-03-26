package com.kevin.vension.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.kevin.vension.demo.ui.activitys.ProxyAvtivity;
import com.kevin.vension.demo.ui.activitys.MainActivity;
import com.vension.frame.observers.FragmentObserver;

import java.util.NoSuchElementException;

/**
 * @author ：Created by vension on 2018/2/28.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * Intent 处理
 */

public class AppCompat {

	/**
	 * 创建Fragment Intent对象
	 */
	static public Intent createAgentIntent(Context context, Class<?> _Class) {
		return createAgentIntent(context, _Class, new Bundle());
	}

	/**
	 * 创建Fragment Intent对象
	 */
	static public Intent createAgentIntent(Context context, Class<?> _Class, Bundle _Bundle) {
		_Bundle.putString(Constant.AGENT_FRAGMENT_CLASS_KEY, _Class.getName()); // com.project.app.activity.*
		Intent _Intent = new Intent(context, ProxyAvtivity.class);
		_Intent.putExtras(_Bundle);
		return _Intent;
	}

	/**
	 * 进入主界面
	 */
	static public void startMainActivity(Activity context){
		Intent intent = new Intent();
		intent.setClass(context, MainActivity.class);
		context.startActivity(intent);
		context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	/**重新登录*/
	public static void resetLogin() {
		try{
			Fragment _CurrentFragment = FragmentObserver.getInstance().getCurrentObserver();
			if (_CurrentFragment != null){
				if(FragmentObserver.getInstance().isOk(_CurrentFragment)) {
//					_CurrentFragment.startActivity(AppCompat.createAgentIntent(_CurrentFragment.getContext(), LoginFragment.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				}
			}else{
//				MainActivity.getInstance().startAgentActivity(LoginFragment.class);
			}
		}catch (NoSuchElementException e){
			e.printStackTrace();
		}
	}


	/**
	 * 从父容器移除View
	 */
	public static View removeInParentView(View view) {
		if(view != null && view.getParent() != null) {
			((ViewGroup) view.getParent()).removeView(view);
		}
		return view;
	}

}
