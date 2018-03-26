package com.vension.customview.paydialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vension.customview.R;

/**
 * @author ：Created by Vension on 2018/3/21 15:43.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class PayPassView extends LinearLayout implements View.OnClickListener{

	private View mView;
	private TextView tvTitle;
	private TextView tvContent;
	public TextView tvTips;
	public TextView tvForgetPass;
	public TextView[] tvPassList;//6个密码输入框
	private TextView[] tvPassNumList;//0-9数字密码

	public LinearLayout layoutKeyboard;
	private LinearLayout paydialog_linear;

	private ImageView ivDelete;
	public ImageView ivClose;
	public MDProgressBar mMDProgressBar;

	private String strPassword;     //输入的密码
	private int currentIndex = -1;    //用于记录当前输入密码格位置
	private OnPasswordInputFinish mOnPasswordInputFinish;

	public PayPassView(Context context) {
		this(context,null);
	}

	public PayPassView(Context context, @Nullable AttributeSet attrs) {

		this(context, attrs,0);
	}

	public PayPassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mView = View.inflate(context, R.layout.layout_pay_pass,null);
		tvTitle = mView.findViewById(R.id.paypass_title);
		tvContent = mView.findViewById(R.id.paypass_content);
		tvTips = mView.findViewById(R.id.paypass_tips);
		tvForgetPass = mView.findViewById(R.id.paypass_forget);

		tvPassList = new TextView[6];
		tvPassList[0] = mView.findViewById(R.id.pay_box1);
		tvPassList[1] = mView.findViewById(R.id.pay_box2);
		tvPassList[2] = mView.findViewById(R.id.pay_box3);
		tvPassList[3] = mView.findViewById(R.id.pay_box4);
		tvPassList[4] = mView.findViewById(R.id.pay_box5);
		tvPassList[5] = mView.findViewById(R.id.pay_box6);

		layoutKeyboard = mView.findViewById(R.id.keyboard);
		paydialog_linear = mView.findViewById(R.id.paydialog_linear);

		tvPassNumList = new TextView[10];
		tvPassNumList[0] = mView.findViewById(R.id.pay_keyboard_zero);
		tvPassNumList[1] = mView.findViewById(R.id.pay_keyboard_one);
		tvPassNumList[2] = mView.findViewById(R.id.pay_keyboard_two);
		tvPassNumList[3] = mView.findViewById(R.id.pay_keyboard_three);
		tvPassNumList[4] = mView.findViewById(R.id.pay_keyboard_four);
		tvPassNumList[5] = mView.findViewById(R.id.pay_keyboard_five);
		tvPassNumList[6] = mView.findViewById(R.id.pay_keyboard_sex);
		tvPassNumList[7] = mView.findViewById(R.id.pay_keyboard_seven);
		tvPassNumList[8] = mView.findViewById(R.id.pay_keyboard_eight);
		tvPassNumList[9] = mView.findViewById(R.id.pay_keyboard_nine);

		ivDelete = mView.findViewById(R.id.pay_keyboard_del);
		ivClose = mView.findViewById(R.id.paypass_close);
		mMDProgressBar = mView.findViewById(R.id.paypass_progress);

		for (int i = 0; i < tvPassNumList.length; i++) {
			tvPassNumList[i].setOnClickListener(this);
		}
		ivDelete.setOnClickListener(this);

		addView(mView);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.pay_keyboard_one) {
			getPassNum("1");
		} else if(id == R.id.pay_keyboard_two){
			getPassNum("2");
		}else if(id == R.id.pay_keyboard_three){
			getPassNum("3");
		}else if(id == R.id.pay_keyboard_four){
			getPassNum("4");
		}else if(id == R.id.pay_keyboard_five){
			getPassNum("5");
		}else if(id == R.id.pay_keyboard_sex){
			getPassNum("6");
		}else if(id == R.id.pay_keyboard_seven){
			getPassNum("7");
		}else if(id == R.id.pay_keyboard_eight){
			getPassNum("8");
		}else if(id == R.id.pay_keyboard_nine){
			getPassNum("9");
		}else if(id == R.id.pay_keyboard_zero){
			getPassNum("0");
		}else if(id == R.id.pay_keyboard_del){
			//删除
			if (currentIndex - 1 >= -1){ //判断是否删除完毕————要小心数组越界
				tvPassList[currentIndex--].setText("");
			}
		}
	}

	/**获取输入的密码*/
	private void getPassNum(String num) {
		if (currentIndex >= -1 && currentIndex < 5){
			tvPassList[++currentIndex].setText(num);
		}
		if (currentIndex == 0){
			if (mOnPasswordInputFinish != null){
				mOnPasswordInputFinish.inputFirst();
			}
		}
	}

	public void setOnFinishInput(OnPasswordInputFinish onFinishInput){
		this.mOnPasswordInputFinish = onFinishInput;
		tvPassList[5].addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				if (editable.toString().length() == 1){
					strPassword = "";//每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
					for (int i = 0; i < tvPassList.length; i++) {
						strPassword += tvPassList[i].getText().toString().trim();
					}
					if (mOnPasswordInputFinish != null){
						//接口中要实现的方法，完成密码输入完成后的响应逻辑
						mOnPasswordInputFinish.inputFinish();
					}
				}
			}
		});
	}

	public void setContent(String content) {
		if (tvContent != null){
			tvContent.setText(content);
		}
	}

	public void setArcColor(int colorId) {
		if(colorId != 0){
			mMDProgressBar.setArcColor(colorId);
		}
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void clearText() {
		for (TextView textview : tvPassList) {
			textview.setText("");
		}
		currentIndex = -1;
	}

}
