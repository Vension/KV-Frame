package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RadioGroup;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.ProgressImageView;
import com.vension.frame.utils.VToastUtil;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class ProgressImageViewFragment extends BaseCustomViewFragment implements RadioGroup.OnCheckedChangeListener{
	@BindView(R.id.image)
	ProgressImageView processImageView;
	@BindView(R.id.id_group)
	RadioGroup mRadioGroup;

	private final int SUCCESS=0;
	private int progress=0;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_progress_imageview;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		mRadioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void lazyLoadData() {

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId){
			case R.id.id_left:
				processImageView.setType(0);
				break;
			case R.id.id_top:
				processImageView.setType(1);
				break;
			case R.id.id_right:
				processImageView.setType(2);
				break;
			case R.id.id_bottom:
				processImageView.setType(3);
				break;
		}
		progress=0;
		initProgress();
	}


	//模拟图片上传进度
	private void initProgress() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					if(progress==100){//图片上传完成
						mHandler.sendEmptyMessage(SUCCESS);
						return;
					}
					progress++;
					processImageView.setProgress(progress);
					try{
						Thread.sleep(100);  //暂停0.1秒
					} catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}).start();
	}



	Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case SUCCESS:
					VToastUtil.showToast("图片上传完成");
					processImageView.setProgressTextColor(R.color.transparent);
					break;
			}
		}
	};

}
