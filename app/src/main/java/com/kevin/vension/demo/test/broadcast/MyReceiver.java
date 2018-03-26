package com.kevin.vension.demo.test.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vension.frame.utils.VToastUtil;

/**
 * @author ：Created by vension on 2018/3/12.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class MyReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String name = intent.getExtras().getString("name");//获取BroadCastActivity中的name标签下的值
		Log.i("Recevier1", "接收到:"+name);
		VToastUtil.showToast("接收到普通广播 MyReceiver 发来的消息：" + name);
	}
}
