package com.kevin.vension.demo.models;

import com.vension.frame.beans.BaseEntity;

/**
 * @author ：Created by vension on 2017/11/15.
 * @email：hqw@kewaimiao.com
 * @desc character determines attitude, attitude determines destiny
 */

public class UserEntity extends BaseEntity {

	/**
	 * id : 3
	 * name : vension
	 * title : cto
	 * mobile : 13660358989
	 * avatar : null
	 * gender : 1
	 * type : 0
	 * email :
	 * password : null
	 * remember_token : null
	 * remark :
	 * status : 0
	 * created_at : 2017-11-15 10:39:07
	 * updated_at : 2017-11-15 10:39:07
	 * identity_card :
	 */

	private int id;
	private String name;
	private String title;
	private String mobile;
	private String avatar;
	private int gender;
	private int type;
	private String email;
	private String password;
	private String remember_token;
	private String remark;
	private int status;
	private String created_at;
	private String updated_at;
	private String identity_card;//身份证


	public String getIdentity_card() {
		return identity_card;
	}

	public void setIdentity_card(String identity_card) {
		this.identity_card = identity_card;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemember_token() {
		return remember_token;
	}

	public void setRemember_token(String remember_token) {
		this.remember_token = remember_token;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
}
