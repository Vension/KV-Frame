package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.kevin.vension.demo.v_custom.utils.TimeTaskScroll;
import com.vension.customview.periscopelayout.PeriscopeLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/27.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class LiveFragment extends BaseCustomViewFragment {
	@BindView(R.id.periscope)
	PeriscopeLayout _PeriscopeLayout;
	@BindView(R.id.lst_message)
	ListView lstMessage;

	List<String> data = new ArrayList<String>();

	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 1:
					_PeriscopeLayout.addHeart();
					break;
				default:
					break;
			}
		}
	};

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_live;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i <= Integer.MAX_VALUE; i++) {
					//periscopeLayout.addHeart();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
					if(i== Integer.MAX_VALUE){
						i=0;
					}
				}
			}
		}).start();

		data = getData();
		new Timer().schedule(new TimeTaskScroll(getActivity(), lstMessage,data), 20, 10);
	}

	private List<String> getData(){
		List<String> data=new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			data.add("This is a test message");
			data.add("created by ilaty");
			data.add("date 27/jun/2016");
			data.add("--------");
		}
		return data;
	}

	@Override
	public void lazyLoadData() {

	}
}
