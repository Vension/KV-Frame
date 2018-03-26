package com.vension.frame.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.vension.frame.R;

/**
 * Created by Administrator on 2017/4/28.
 */

public abstract class V_BaseDialogFragment extends DialogFragment {

	private static final String TGA = "V_BaseDialogFragment";

	public abstract int createView();
	public abstract void initViews(View parent);

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//如果setCancelable()中参数为true，若点击dialog覆盖不到的activity的空白或者按返回键，则进行cancel，状态检测依次onCancel()和onDismiss()。如参数为false，则按空白处或返回键无反应。缺省为true
		setCancelable(false);
		//可以设置dialog的显示风格，如style为STYLE_NO_TITLE，将被显示title。遗憾的是，我没有在DialogFragment中找到设置title内容的方法。theme为0，表示由系统选择合适的theme。
		int style = DialogFragment.STYLE_NORMAL, theme = 0;
		setStyle(style,theme);
	}



	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		//设置dialog的 进出 动画
		getDialog().getWindow().setWindowAnimations(R.style.DialogStyleOfDefault);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

//	@Override
//	public final Dialog onCreateDialog(Bundle savedInstanceState) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//		LayoutInflater inflater = getActivity().getLayoutInflater();
//		View view = inflater.inflate(createView(),null);
////		ButterKnife.bind(this,view);
//		initViews(view);
//		builder.setView(view);
//		Dialog dialog = builder.create();
//		return dialog;
//	}



	@Override
	public void show(FragmentManager manager, String tag) {
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.add(this, tag);
		transaction.commitAllowingStateLoss();// 防止按home键后，show的时候调用父类中的commit方法异常
	}



	@Override //仅用于状态跟踪
	public void onCancel(DialogInterface dialog) {
		Log.i("TGA","onCancel() is called");
		super.onCancel(dialog);
	}

	@Override  //仅用户状态跟踪
	public void onDismiss(DialogInterface dialog) {
		Log.i("TGA","onDismiss() is called");
		super.onDismiss(dialog);
	}

//
//	public void showDialog(){
//		FragmentManager fm = getActivity().getSupportFragmentManager();
//		EquipmentTestDialogFragment dialoga = new EquipmentTestDialogFragment();
//		dialoga.show(fm, "fragmenta");
//	}



}
