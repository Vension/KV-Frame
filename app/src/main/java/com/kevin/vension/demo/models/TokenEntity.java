package com.kevin.vension.demo.models;

import android.text.TextUtils;

import java.io.Serializable;

/**
 */
public class TokenEntity implements Serializable {

	private String access_token;

	private String token_type;

	private int expires_in;

	public TokenEntity() {
		super();
	}

	public String getAccess_token() {
		return TextUtils.isEmpty(access_token) ? "354a69431ff974c888bcd09bb1b188ca16bc3caa" : access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
