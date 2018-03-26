package com.kevin.vension.demo.http;

import java.io.Serializable;

/**
 * Created by Vension on 17/9/7.
 * 服务器通用返回数据格式
 */

public class MyHttpResponse<T> implements Serializable {

    public MyHttpResponse(boolean success, String error, int code, T data) {
        this.success = success;
        this.data = data;
        this.code = code;
        this.error = error;
    }

    /**
     * success : true
     * error :
     * data : {"access_token":"be2b8c336231b8e92d11165be6f793ced6e882eb","expires_in":2628000,"token_type":"Bearer"}
     * code : 0
     */



    private boolean success;
    private String error;
    private int code;
    private T data;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
