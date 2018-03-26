package com.vension.customview.paydialog;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.vension.customview.R;

/**
 * @author ：Created by Vension on 2018/3/21 16:31.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 * 仿支付宝密码支付输入弹窗
 */

public class PayDialog extends Dialog implements OnPasswordInputFinish{

	public PayPassView mPayPassView;
	private PayInterface mPayInterface;
	private boolean isClear = false;
	private String content;
	private int colorId;

	public PayDialog(@NonNull Context context,String content,PayInterface payInterface) {
		super(context, R.style.BottomDialogStyle);
		this.content = content;
		this.mPayInterface = payInterface;

		//拿到Dialog的Window，修改Window的属性
		Window window = getWindow();
		window.getDecorView().setPadding(0,0,0,0);
		//获取Window的LayoutParams
		setCancelable(false);
		setCanceledOnTouchOutside(false);
		WindowManager.LayoutParams attributes = window.getAttributes();
		attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
		attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		//一定要重新设置属性才能生效
		window.setAttributes(attributes);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化
		mPayPassView = new PayPassView(getContext());
		//设置内容，如支付金额：XX元
		mPayPassView.setContent(content);
		mPayPassView.setArcColor(colorId);
		setContentView(mPayPassView);
		mPayPassView.setOnFinishInput(this);

		mPayPassView.ivClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				////当progress显示时，说明在请求网络，这时点击关闭不作处理
				if(mPayPassView.mMDProgressBar.getVisibility()!= View.VISIBLE) {
					cancel();
				}
			}
		});

		mPayPassView.mMDProgressBar.setOnPasswordCorrectlyListener(new MDProgressBar.OnPasswordCorrectlyListener() {
			@Override
			public void onPasswordCorrectly() {
				//progress动画完成后 回调
				if(mPayInterface != null){
					mPayInterface.onSuccess();
				}
			}
		});

		mPayPassView.tvForgetPass.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mPayInterface != null){
					mPayInterface.onForget();
				}
			}
		});
	}

	@Override
	public void inputFinish() {
		//输入完成
		mPayPassView.mMDProgressBar.setVisibility(View.VISIBLE);
		mPayPassView.layoutKeyboard.setVisibility(View.INVISIBLE);
		if (mPayInterface != null){
			mPayInterface.payFinish(mPayPassView.getStrPassword());
		}
	}

	@Override
	public void inputFirst() {
		mPayPassView.tvTips.setVisibility(View.INVISIBLE);
	}

	public void setSuccess() {
		mPayPassView.mMDProgressBar.setSuccessfullyStatus();
	}

	public void setError(String errorText) {
		mPayPassView.tvTips.setVisibility(View.VISIBLE);
		mPayPassView.tvTips.setText(errorText);
		check();
	}

	public void check(){
		isClear = false;
		TextView[] textViews =  mPayPassView.tvPassList;
		for (TextView textview:textViews) {
			YoYo.with(Techniques.Shake)
					.duration(500)
					.repeat(0)
					.onEnd(new YoYo.AnimatorCallback() {
						@Override
						public void call(Animator animator) {
							if(!isClear){
								mPayPassView.clearText();
								mPayPassView.mMDProgressBar.setVisibility(View.GONE);
								mPayPassView.layoutKeyboard.setVisibility(View.VISIBLE);
								isClear = true;
							}
						}
					})
					.playOn(textview);
		}
		YoYo.with(Techniques.Shake)
				.duration(500)
				.repeat(0)
				.playOn(mPayPassView.tvTips);
	}

	/**
	 * 设置progress的颜色
	 */
	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	public interface PayInterface{
		void payFinish(String password);
		void onSuccess();
		void onForget();
	}

}
