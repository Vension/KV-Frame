package com.vension.frame.utils.stack_managers;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author ：Created by vension on 2018/1/19.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 使用 弱引用 WeakReference 保存每个Activity对象, 有效的保证Activity回收时不被调用
 */

public class ActivityStackManager extends ObjectStackManager<Activity> {
	private static final String TAG = "ActivityStackManager";
	private static ActivityStackManager _StackManager;

	private ActivityStackManager() {
		super();
	}


	/***
	 * 获得AppManager的实例
	 *
	 * @return AppManager实例
	 */
	public static ActivityStackManager getInstance() {
		if (_StackManager == null) {
			_StackManager = new ActivityStackManager();
		}
		return _StackManager;
	}

	/**添加Activity到栈中*/
	synchronized public boolean addActivity(WeakReference<Activity> activity) {
		return addObjectInStack(activity);
	}


	/**
	 * 销毁指定Activity
	 *
	 * @param activity 弱引用的ac
	 */
	public boolean removeActivity(WeakReference<Activity> activity) {
		if (activity != null){
			killActivity(activity);
		}
		return removeObjectOutStack(activity);
	}



	@Override
	public void clear() {
		/**销毁所有Activity*/
		killAllActivity();
		super.clear();
	}

	/**
	 * 结束所有Activity
	 */
	public void killAllActivity() {
		try {
			ListIterator<WeakReference<Activity>> listIterator = getStack().listIterator();
			while (listIterator.hasNext()) {
				Activity activity = listIterator.next().get();
				if (activity != null) {
					activity.finish();
				}
				listIterator.remove();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}


	/**
	 * 结束栈顶Activity（堆栈中最后一个压入的）
	 */
	public void killTopActivity() {
		try {
			WeakReference<Activity> activity = getStack().lastElement();
			killActivity(activity);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	/***
	 * 结束指定的Activity
	 *
	 * @param activity
	 */
	public void killActivity(WeakReference<Activity> activity) {
		try {
			Iterator<WeakReference<Activity>> iterator = getStack().iterator();
			while (iterator.hasNext()) {
				WeakReference<Activity> stackActivity = iterator.next();
				if (stackActivity.get() == null) {
					iterator.remove();
					continue;
				}
				if (stackActivity.get().getClass().getName().equals(activity.get().getClass().getName())) {
					iterator.remove();
					stackActivity.get().finish();
					break;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}


	/***
	 * 结束指定类名的Activity
	 *
	 * @param cls
	 */
	public void killActivity(Class<?> cls) {
		try {
			ListIterator<WeakReference<Activity>> listIterator = getStack().listIterator();
			while (listIterator.hasNext()) {
				Activity activity = listIterator.next().get();
				if (activity == null) {
					listIterator.remove();
					continue;
				}
				if (activity.getClass() == cls) {
					listIterator.remove();
					if (activity != null) {
						activity.finish();
					}
					break;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}



	/**
	 * 移除除了某个activity的其他所有activity
	 *
	 * @param cls 界面
	 */
	public void killAllActivityExceptOne(Class cls) {
		try {
			for (int i = 0; i < stackSize(); i++) {
				WeakReference<Activity> activity = getStack().get(i);
				if (activity.getClass().equals(cls)) {
					break;
				}
				if (getStack().get(i) != null) {
					killActivity(activity);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}


	/**
	 * 退出应用程序
	 */
	synchronized public void AppExit() {
		// 清理
		this.clear();
		// 退出程序
		System.exit(0);
		// 杀死进程
		android.os.Process.killProcess(android.os.Process.myPid());
		// 通知系统回收
		System.gc();
	}
}
