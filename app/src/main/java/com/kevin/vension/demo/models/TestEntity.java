package com.kevin.vension.demo.models;

import com.vension.frame.beans.BaseEntity;

/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class TestEntity extends BaseEntity {

	private int id;
	private int imgResId;
	private String title;
	private String desc;
	private String date;
	private boolean isSelected;

	public TestEntity(int imgResId, String title, String desc, String date) {
		this.imgResId = imgResId;
		this.title = title;
		this.desc = desc;
		this.date = date;
	}

	public TestEntity(String title) {
		this.title = title;
	}

	public TestEntity(String title,String desc) {
		this.title = title;
		this.desc = desc;
	}

	public TestEntity(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImgResId() {
		return imgResId;
	}

	public void setImgResId(int imgResId) {
		this.imgResId = imgResId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


}
