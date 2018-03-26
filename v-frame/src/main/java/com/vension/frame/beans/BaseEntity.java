package com.vension.frame.beans;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class BaseEntity implements MultiItemEntity,Serializable {
	@Override
	public int getItemType() {
		return 0;
	}
}
