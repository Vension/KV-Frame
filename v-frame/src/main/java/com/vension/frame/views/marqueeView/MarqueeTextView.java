package com.vension.frame.views.marqueeView;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewDebug;

/**
 * Created by vension on 2017/10/23.
 * 跑马灯效果的textview
 * @use    <***.RxRunTextView
            android:ellipsize="marquee"
            android:focusable="true"
            android:marqueeRepeatLimit="marquee_forever"
            android:scrollHorizontally="true"
            android:singleLine="true"/>
 */

public class MarqueeTextView extends AppCompatTextView {

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeTextView(Context context) {
		super(context);
	}

	/**
	 * 当前并没有焦点，我只是欺骗了Android系统
	 */
	@Override
	@ViewDebug.ExportedProperty(category = "focus")
	public boolean isFocused() {
		return true;
	}


}
