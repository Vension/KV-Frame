package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.RecordView;

import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vension.customview.RecordView.MODEL_PLAY;

/**
 * @author ：Created by Vension on 2018/3/22 14:56.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class RecordAnimationViewFragment extends BaseCustomViewFragment implements View.OnTouchListener {
	
	@BindView(R.id.recordView)
	RecordView mRecordView;
	@BindView(R.id.button)
	Button mButton;

	private int nowModel = RecordView.MODEL_RECORD;
	private TimerTask timeTask;
	private Timer timeTimer = new Timer(true);
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int db = (int) (Math.random()*100);
			mRecordView.setVolume(db);
		}
	};


	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_recordview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mRecordView.setCountdownTime(9);
		mRecordView.setModel(RecordView.MODEL_RECORD);
		mButton.setOnTouchListener(this);
	}

	@Override
	public void lazyLoadData() {

	}


	@OnClick(R.id.button2)
	public void onClick(){
		if(nowModel == MODEL_PLAY){
			mRecordView.setModel(RecordView.MODEL_RECORD);
			nowModel = RecordView.MODEL_RECORD;
		}else{
			mRecordView.setModel(MODEL_PLAY);
			nowModel = MODEL_PLAY;
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			mRecordView.start();
			timeTimer.schedule(timeTask = new TimerTask() {
				public void run() {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}, 20, 20);
			mRecordView.setOnCountDownListener(new RecordView.OnCountDownListener() {
				@Override
				public void onCountDown() {
					Toast.makeText(getContext(),"计时结束啦~~",Toast.LENGTH_SHORT).show();
				}
			});
		}else if(event.getAction() == MotionEvent.ACTION_UP){
			mRecordView.cancel();
		}
		return false;
	}
}
