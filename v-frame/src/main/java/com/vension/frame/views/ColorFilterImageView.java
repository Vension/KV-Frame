package com.vension.frame.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author ：Created by vension on 2018/2/11.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 实现图像根据按下抬起动作变化颜色
 */

public class ColorFilterImageView extends AppCompatImageView implements View.OnTouchListener {

	public ColorFilterImageView(Context context) {
		this(context, null, 0);
	}

	public ColorFilterImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ColorFilterImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:  // 按下时图像变灰
				setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
				break;
			case MotionEvent.ACTION_UP:   // 手指离开或取消操作时恢复原色
			case MotionEvent.ACTION_CANCEL:
				setColorFilter(Color.TRANSPARENT);
				break;
			default:
				break;
		}
		return false;
	}

}
