package com.vension.frame.utils.navbar;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import java.lang.reflect.Field;

/**
 * @author ：Created by vension on 2018/2/28.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 用反射，改变 item 中 mShiftingMode 的值
 */

public class BottomNavigationViewHelper {

	public static void disableShiftMode(BottomNavigationView navigationView) {
		BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
		try {
			Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
			shiftingMode.setAccessible(true);
			shiftingMode.setBoolean(menuView, false);
			shiftingMode.setAccessible(false);

			for (int i = 0; i < menuView.getChildCount(); i++) {
				BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
				itemView.setShiftingMode(false);
				itemView.setChecked(itemView.getItemData().isChecked());
			}

		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
