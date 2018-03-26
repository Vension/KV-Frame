package com.kevin.vension.demo.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.kevin.vension.demo.http.Api;
import com.kevin.vension.demo.models.TokenEntity;
import com.kevin.vension.demo.models.UserEntity;
import com.vension.frame.VFrame;
import com.vension.frame.observers.ActivityObserver;
import com.vension.frame.observers.FragmentObserver;
import com.vension.frame.utils.SharedPreferenceUtil;
import com.vension.frame.utils.VAppUtil;

/**
 * @author ：Created by vension on 2017/12/15.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 *  存储账户相关信息
 */

public class UserACache {

	final private String ACACHE_USER_ENTITY_KEY = "UserEntity Key";//保存用户信息的key
	final private String ACACHE_USER_NAME_KEY = "Contact name Key";//用户名
	final private String ACACHE_TOKEN_ENTITY_KEY = "TokenEntity Key";//保存accessToken的key
	final private String ACACHE_TOKEN_START_TIME_KEY = "Token Start Time Key";//token开始时间
	final private String ACACHE_TOKEN_INVALID_TIME_KEY = "Token Invalid Time Key";//token有效时间--秒


	final private SharedPreferenceUtil _SharedACache;
	static private UserACache _UserACache;

	static public UserACache getInstance() {
		if(_UserACache == null) {
			_UserACache = new UserACache();
		}
		return _UserACache;
	}

	private UserACache() {
		_SharedACache = new SharedPreferenceUtil(VFrame.getContext(), VAppUtil.getAppPackageName() + ".UserACache.do");
	}

	public boolean hasInit() {
		return isTokenInvalid() || !isLogin();
	}

	synchronized public boolean isLogin() {
		return !TextUtils.isEmpty(_SharedACache.getString(ACACHE_USER_ENTITY_KEY));
	}

	synchronized public UserEntity getUserEntity() {
		UserEntity _UserModel = new UserEntity();
		String _UserString = _SharedACache.getString(ACACHE_USER_ENTITY_KEY);
		if(!TextUtils.isEmpty(_UserString)) {
			_UserModel = new Gson().fromJson(_UserString, UserEntity.class);
		}
		return _UserModel;
	}

	synchronized public TokenEntity getTokenModel() {
		TokenEntity _TokenModel = new TokenEntity();
		String _TokenString = _SharedACache.getString(ACACHE_TOKEN_ENTITY_KEY);
		if(!TextUtils.isEmpty(_TokenString)) {
			_TokenModel = new Gson().fromJson(_TokenString, TokenEntity.class);
		}
		return _TokenModel;
	}

	synchronized public String getAccessToken() {
		TokenEntity _TokenModel = getTokenModel();
		if(null == _TokenModel) {
			return Api.SERVER_TOKEN;
		}
		String _TokenType = _TokenModel.getToken_type();
		String _AccessToken = _TokenModel.getAccess_token();
		if(TextUtils.isEmpty(_TokenType) || TextUtils.isEmpty(_AccessToken)) {
			return Api.SERVER_TOKEN;
		}
		return _TokenModel.getToken_type() + " " + _TokenModel.getAccess_token();
	}

	/**判断Token是否失效*/
	synchronized public boolean isTokenInvalid() {
		try {
			String _TokenString = _SharedACache.getString(ACACHE_TOKEN_ENTITY_KEY);
			if(TextUtils.isEmpty(_TokenString)) {
				return true;
			}

			long _StartTime = _SharedACache.getLong(ACACHE_TOKEN_START_TIME_KEY);
			long _InvalidTime = (long) _SharedACache.getInt(ACACHE_TOKEN_INVALID_TIME_KEY);
			long _CurrentTime = System.currentTimeMillis() / 1000;
			if(_InvalidTime <= 0 || _StartTime <= 0) {
				return true;
			}

			// exceed max time for Token invalid
			if(_CurrentTime - _StartTime > _InvalidTime) {
				return invalid();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	synchronized public boolean invalid() {
		_SharedACache
				.remove(ACACHE_TOKEN_START_TIME_KEY)
				.remove(ACACHE_TOKEN_INVALID_TIME_KEY)
				.commit();
		return true;
	}

	synchronized public void setToken(TokenEntity model) {
		long currentTimeMillis = System.currentTimeMillis() / 1000;
		_SharedACache
				.put(ACACHE_TOKEN_ENTITY_KEY, new Gson().toJson(model))
				.put(ACACHE_TOKEN_START_TIME_KEY, currentTimeMillis)
				.put(ACACHE_TOKEN_INVALID_TIME_KEY,model.getExpires_in())
				.commit();
	}


	synchronized public void setUser(UserEntity model) {
		setUser(getLastUsername(),model);
	}

	synchronized public void setUser(String username, UserEntity model) {
		_SharedACache
				.put(ACACHE_USER_NAME_KEY, username)
				.put(ACACHE_USER_ENTITY_KEY, new Gson().toJson(model))
				.commit();
	}


	synchronized public void logout() {
		_SharedACache
				.remove(ACACHE_USER_ENTITY_KEY)
				.remove(ACACHE_TOKEN_START_TIME_KEY)
				.remove(ACACHE_TOKEN_INVALID_TIME_KEY)

				.commit();
	}


	synchronized public void resetLogin() {
		try {
			// 清理登录信息
			UserACache.getInstance().logout();
			// 跳转登录
			AppCompat.resetLogin();
			// 清理Activity和Fragment
			FragmentObserver.getInstance().clear();
			ActivityObserver.getInstance().clear();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	synchronized public String getLastUsername() {
		return _SharedACache.getString(ACACHE_USER_NAME_KEY);
	}

	synchronized public String getUserId() {
		if(isLogin()) {
			return String.valueOf(getUserEntity().getId());
		}
		return "0";
	}

}
