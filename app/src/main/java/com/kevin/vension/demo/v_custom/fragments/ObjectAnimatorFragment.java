package com.kevin.vension.demo.v_custom.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/25.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class ObjectAnimatorFragment extends BaseCustomViewFragment implements ValueAnimator.AnimatorUpdateListener{

	@BindView(R.id.btn_age_change)
	Button btnAgeChange;
	@BindView(R.id.bt_property)
	Button btProperty;
	@BindView(R.id.tv_property_info)
	TextView tvPropertyInfo;

	private ObjectAnimator objectAnimator;
	protected Person person;
	private boolean isAge;

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_object_animator;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		person = new Person();
		person.setName("张三");
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick({R.id.btn_age_change, R.id.bt_property})
	public void onClick(View v){
		switch (v.getId()){
			case R.id.btn_age_change:
				isAge = true;
				objectAnimator = ObjectAnimator.ofInt(person, "age", 1, 100);
				objectAnimator.addUpdateListener(this);
				objectAnimator.setDuration(5000);
				objectAnimator.start();
				break;
			case R.id.bt_property:
				isAge = false;
				objectAnimator = ObjectAnimator.ofFloat(tvPropertyInfo, "TranslationY", 0, 300);
				objectAnimator.addUpdateListener(this);
				objectAnimator.setDuration(500);
				objectAnimator.start();
				break;
		}
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		if (isAge){
			int values = (int) animation.getAnimatedValue();
			person.setAge(values);
			tvPropertyInfo.setText(person.toString());
		}
	}



	class Person {
		private String name;
		private int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person{" +
					"name='" + name + '\'' +
					", age=" + age +
					'}';
		}
	}

}
