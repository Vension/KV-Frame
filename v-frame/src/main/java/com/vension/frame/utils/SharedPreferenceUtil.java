package com.vension.frame.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

/**
 * Created by Administrator on 2017/3/22.
 * @author Vension ==> hqw@kewaimiao.com
 * 偏好共享
 */
public class SharedPreferenceUtil {

    private SharedPreferences mSharedPreferences;
    private Editor mEditor;

    private static ThreadLocal<SharedPreferenceUtil> mThreadLocal;

    static {
        mThreadLocal = new ThreadLocal<>();
    }


    public SharedPreferenceUtil(Context mContext) {
        this(mContext,mContext.getPackageName() + ".Share");
    }

    @SuppressLint("WrongConstant")
	public SharedPreferenceUtil(Context mContext, String fileName) {
        mSharedPreferences = mContext.getSharedPreferences(fileName, (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) ? 4 : 0);
    }


    /**获取偏好保存单一实例*/
    public static SharedPreferenceUtil getSingleInstance(Context mContext){
        SharedPreferenceUtil mSharedACache = mThreadLocal.get();
        if (mSharedACache == null){
            mSharedACache = new SharedPreferenceUtil(mContext);
            mThreadLocal.set(mSharedACache);
        }
        return  mSharedACache;
    }

    /**初始化*/
    public SharedPreferenceUtil init(){
        if (mEditor == null){
            mEditor = mSharedPreferences.edit();
        }
        return this;
    }


    public SharedPreferenceUtil put(String key, String vaule){
        init().mEditor.putString(key,vaule);
        return this;
    }


    public SharedPreferenceUtil put(String key, long value) {
        init().mEditor.putLong(key, value);
        return this;
    }

    public SharedPreferenceUtil put(String key, boolean value) {
        init().mEditor.putBoolean(key, value);
        return this;
    }

    public SharedPreferenceUtil put(String key, int value) {
        init().mEditor.putInt(key, value);
        return this;
    }

    public boolean isExist(String key) {
        return mSharedPreferences.contains(key);
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String def) {
        return mSharedPreferences.getString(key, def);
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0l);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, Boolean def) {
        return mSharedPreferences.getBoolean(key, def);
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    public SharedPreferenceUtil remove(String key) {
        init().mEditor.remove(key);
        return this;
    }

    public void clear() {
        init().mEditor.clear().commit();
    }

    public void commit() {
        init().mEditor.commit();
    }

}
