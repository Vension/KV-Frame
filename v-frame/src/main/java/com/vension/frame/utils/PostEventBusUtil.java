package com.vension.frame.utils;


import com.vension.frame.beans.BaseEventBusBean;

import org.greenrobot.eventbus.EventBus;

/**
 * @author ：Created by vension on 2018/2/11.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * eventBus发送消息帮助类
 */

public class PostEventBusUtil {

	//创建单例模式第一步：将构造方法私有化private,不允许外部直接创建对象
	private PostEventBusUtil() {

	}

	//在类中创建类的唯一实例,加static，为了安全起见最好将实例对象私有化private
	private static PostEventBusUtil instance = new PostEventBusUtil();

	//提供一个用于获取实例的方法,必须static
	public static PostEventBusUtil getInstance() {
		return instance == null ? new PostEventBusUtil() : instance;
	}

	/**
	 * 发送消息
	 *
	 * @param bean 发送数据类
	 * @param <T>  必须继承于BaseEventBusBean
	 */
	public <T extends BaseEventBusBean> void postMessage(final T bean) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				EventBus.getDefault().post(bean);
			}
		}).start();
	}

	/**
	 * 发送粘性消息
	 *
	 * @param bean 发送数据类
	 * @param <T>  必须继承于BaseEventBusBean
	 */
	public <T extends BaseEventBusBean> void postStickMessage(final T bean) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				EventBus.getDefault().postSticky(bean);
			}
		}).start();
	}

}
