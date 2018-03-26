package com.kevin.vension.demo.test.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vension.frame.utils.VToastUtil;

/**
 * @author ：Created by Vension on 2018/3/23 14:47.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		VToastUtil.showToast("BootCompleteReceiver onReceive");
	}

}
