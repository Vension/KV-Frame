package com.kevin.vension.demo.test.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author ：Created by vension on 2018/3/12.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class MyReceiver2 extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String name = intent.getExtras().getString("name");
		Log.i("Recevier2", "接收到:"+name);
	}
}
