package com.kevin.vension.demo.v_custom.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.kevin.vension.demo.v_custom.adapters.LVLiveMessageAdapter;

import java.util.List;
import java.util.TimerTask;

/**
 * ilaty
 * date:2016-06-29
 * */
public class TimeTaskScroll extends TimerTask {
	
	private ListView listView;
	
	public TimeTaskScroll(Context context, ListView listView, List<String> list){
		this.listView = listView;
		listView.setAdapter(new LVLiveMessageAdapter(context, list));
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			listView.smoothScrollBy(1, 0);
		};
	};

	@Override
	public void run() {
		Message msg = handler.obtainMessage();
		handler.sendMessage(msg);
	}

}
