package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import com.vension.customview.datepicker.TimePickerDialog;
import com.vension.customview.datepicker.data.Type;
import com.vension.customview.datepicker.listener.OnDateSetListener;
import com.vension.customview.timepicker.TimeSelector;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class DatePickerFragment extends BaseCustomViewFragment implements OnDateSetListener {
	@BindView(R.id.button)
	Button button;
	@BindView(R.id.btn_all)
	Button btnAll;
	@BindView(R.id.btn_year_month_day)
	Button btnYearMonthDay;
	@BindView(R.id.btn_year_month)
	Button btnYearMonth;
	@BindView(R.id.btn_month_day_hour_minute)
	Button btnMonthDayHourMinute;
	@BindView(R.id.btn_hour_minute)
	Button btnHourMinute;
	@BindView(R.id.tv_time)
	TextView tvTime;
	private TimeSelector timeSelector;

	TimePickerDialog mDialogAll;
	TimePickerDialog mDialogYearMonth;
	TimePickerDialog mDialogYearMonthDay;
	TimePickerDialog mDialogMonthDayHourMinute;
	TimePickerDialog mDialogHourMinute;
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_datepicker;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
			@Override
			public void handle(String time) {
				Toast.makeText(getActivity(), time, Toast.LENGTH_LONG).show();
			}
		}, "1989-01-30 00:00", "2018-12-31 00:00");

//        timeSelector.setIsLoop(false);

		long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
		mDialogAll = new TimePickerDialog.Builder()
				.setCallBack(this)
				.setCancelStringId("Cancel")
				.setSureStringId("Sure")
				.setTitleStringId("TimePicker")
				.setYearText("Year")
				.setMonthText("Month")
				.setDayText("Day")
				.setHourText("Hour")
				.setMinuteText("Minute")
				.setCyclic(false)
				.setMinMillseconds(System.currentTimeMillis())
				.setMaxMillseconds(System.currentTimeMillis() + tenYears)
				.setCurrentMillseconds(System.currentTimeMillis())
				.setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
				.setType(Type.ALL)
				.setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
				.setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
				.setWheelItemTextSize(12)
				.build();

//        mDialogAll = new TimePickerDialog.Builder()
//                .setMinMillseconds(System.currentTimeMillis())
//                .setThemeColor(R.color.colorPrimary)
//                .setWheelItemTextSize(16)
//                .setCallBack(this)
//                .build();
		mDialogYearMonth = new TimePickerDialog.Builder()
				.setType(Type.YEAR_MONTH)
				.setThemeColor(getResources().getColor(R.color.colorPrimary))
				.setCallBack(this)
				.build();
		mDialogYearMonthDay = new TimePickerDialog.Builder()
				.setType(Type.YEAR_MONTH_DAY)
				.setCallBack(this)
				.build();
		mDialogMonthDayHourMinute = new TimePickerDialog.Builder()
				.setType(Type.MONTH_DAY_HOUR_MIN)
				.setCallBack(this)
				.build();
		mDialogHourMinute = new TimePickerDialog.Builder()
				.setType(Type.HOURS_MINS)
				.setCallBack(this)
				.build();
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick(R.id.button)
	public void onClick() {
		timeSelector.show();
	}


	@OnClick({R.id.btn_all, R.id.btn_year_month, R.id.btn_year_month_day, R.id.btn_month_day_hour_minute, R.id.btn_hour_minute})
	public void onshowDatePickerDialog(View view){
		switch (view.getId()) {
			case R.id.btn_all:
				mDialogAll.show(getActivity().getSupportFragmentManager(), "all");
				break;
			case R.id.btn_year_month:
				mDialogYearMonth.show(getActivity().getSupportFragmentManager(), "year_month");
				break;
			case R.id.btn_year_month_day:
				mDialogYearMonthDay.show(getActivity().getSupportFragmentManager(), "year_month_day");
				break;
			case R.id.btn_month_day_hour_minute:
				mDialogMonthDayHourMinute.show(getActivity().getSupportFragmentManager(), "month_day_hour_minute");
				break;
			case R.id.btn_hour_minute:
				mDialogHourMinute.show(getActivity().getSupportFragmentManager(), "hour_minute");
				break;
		}
	}

	@Override
	public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
		String text = getDateToString(millseconds);
		tvTime.setText(text);
	}


	public String getDateToString(long time) {
		Date d = new Date(time);
		return sf.format(d);
	}

}
