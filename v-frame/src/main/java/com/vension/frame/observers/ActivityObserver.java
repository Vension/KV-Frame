package com.vension.frame.observers;


import android.app.Activity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 * @author Vension ==> hqw@kewaimiao.com
 * activity管理
 * TODO (后续 或许考虑使用 弱引用 WeakReference 保存每个Activity对象, 有效的保证Activity回收时不被调用 )
 */
public class ActivityObserver extends ObjectObserver<Activity> {

	private static ActivityObserver _Observer;

	private ActivityObserver() {
		super();
	}


	/**实例化*/
	static synchronized public ActivityObserver getInstance() {
		if(_Observer == null) {
			_Observer = new ActivityObserver();
		}
		return _Observer;
	}


	/**添加Activity到栈中*/
	synchronized public boolean regist(Activity activity) {
		return registObjectObserver(activity);
	}

	/**销毁指定Activity*/
	synchronized public boolean unregist(Activity activity) {
		activity.finish();
		System.gc();
		return unregistObjectObserver(activity);
	}

	/**销毁当前Activity*/
	synchronized public boolean unregist() {
		return unregist(getCurrentObserver());
	}

	/** 结束所有Activity跳转主界面
	 * @param mainActivity*/
	synchronized public void finishAgentActivity(Class<?> mainActivity){
		List<Activity> mObserver = getObserverList();
		if (!mObserver.isEmpty()){
			for (Activity mActivity : mObserver) {
				if (!mActivity.getClass().isInstance(mainActivity)) {
					mActivity.finish();
				}
			}
		}
	}


	/**退出应用程序*/
	synchronized public void AppExit() {
		try {
			// 清理
			this.clear();
			// 杀死进程
			android.os.Process.killProcess(android.os.Process.myPid());
			// 退出程序
			System.exit(0);
			// 通知系统回收
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void clear() {
		/**销毁所有Activity*/
		synchronized (this) {
			List<Activity> mObservers =  getObserverList();
			if (!mObservers.isEmpty()) {
				for(Activity mActivity : mObservers) {
					if(!mActivity.isFinishing()) {
						mActivity.finish();
					}
				}
			}
		}
		super.clear();
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO
	}

	@Override
	public void notifyDataSetChanged(Activity activity) {
		// TODO
	}

	@Override
	public void notifyDataSetChanged(Class<? extends Activity> _Class) {
		// TODO
	}



}
