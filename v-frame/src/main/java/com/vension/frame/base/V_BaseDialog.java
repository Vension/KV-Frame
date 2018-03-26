package com.vension.frame.base;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.vension.frame.R;

/**
 * @author ：Created by vension on 2018/3/5.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public abstract class V_BaseDialog extends AppCompatDialog implements View.OnClickListener{

	protected Context _Context;
	private Object _Object;

	public V_BaseDialog(Context context) {
		super(context, R.style.DialogStyleOfDefault);
		this._Context = context;
	}

	public V_BaseDialog(Context context, int themeId) {
		super(context, themeId);
		this._Context = context;
	}

	protected V_BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this._Context = context;
	}


//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setAnimationStyle(R.style.DialogStyleOfDefault);//设置弹窗动画
//		setContentView(bindLayoutId());//
//		ButterKnife.bind(this);
//		final WindowManager.LayoutParams fLayoutParams = getWindow().getAttributes();
//		fLayoutParams.dimAmount = 0.3f;
//		fLayoutParams.gravity = Gravity.CENTER;
//		getWindow().setAttributes(fLayoutParams);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//		this.init();
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			this.cancel();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void setAnimationStyle(int styleId) {
		this.getWindow().setWindowAnimations(styleId);
	}


	/** 设置Dialog屏幕比例*/
	protected void setScreenScale(int widthScale, int heightScale) {
		DisplayMetrics _Metrics = new DisplayMetrics();
		this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(_Metrics);
		int width = widthScale == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : widthScale == -1 ? ViewGroup.LayoutParams.MATCH_PARENT : _Metrics.widthPixels - (_Metrics.widthPixels / widthScale);
		int height = heightScale == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : heightScale == -1 ? ViewGroup.LayoutParams.MATCH_PARENT : _Metrics.heightPixels - (_Metrics.heightPixels / heightScale);
		this.getWindow().setLayout(width, height);
	}

	public Object getTag() {
		return _Object;
	}

	public V_BaseDialog setTag(Object obj) {
		this._Object = obj;
		return this;
	}


	@Override
	public void onClick(View view) {
		//TODO 点击事件
	}

	protected abstract int bindLayoutId();//绑定dialog布局
	protected abstract void init();//初始化views



}
