package com.kevin.vension.demo.http;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author ：Created by vension on 2017/12/1 15:31.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 * Http同意回调处理
 */

public abstract class HttpCallBack<T> implements Observer<MyHttpResponse<T>> {

	//onSubscribe()：RxJava 2.0 中新增的，传递参数为Disposable ，Disposable 相当于RxJava1.x中的Subscription,用于解除订阅。
	private Disposable _Disposable;

	@Override
	public void onSubscribe(@NonNull Disposable d) {
		this._Disposable = d;
	}

	@Override
	public void onNext(@NonNull MyHttpResponse<T> httpResponse) {
		Log.i("info", "请求回调:获取数据完成============="+httpResponse.isSuccess());
		if (httpResponse.isSuccess()) {
			onSuccess((T) httpResponse.getData());
		}else{
			onFailure(httpResponse.getError());
		}
	}

	@Override
	public void onError(Throwable e) {
		//请求失败，此时做show error layout操作
		Log.i("info", "请求回调:onError");
		String mMessage = "";
		// 连接错误才执行
		if(e instanceof HttpException){
			HttpException mHttpException = (HttpException)e;
			int code = mHttpException.response().code();
			Log.i("info","code:"+code);
			switch (mHttpException.code()) {
				case 504:
					mMessage = "网络中断，请检查您的网络状态";
					break;
			}
		} else if (e instanceof NetworkErrorException){
			mMessage = "网络未连接，请检查您的网络状态";
		}else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
			mMessage = "连接超时";
		}else if (e instanceof JsonParseException
				|| e instanceof JSONException) {
			Log.i("info","code:解析错误");
			mMessage = "解析错误";
		} else if (e instanceof ConnectException) {
			Log.i("info","code:连接失败");
			mMessage = "连接失败";
		} else if (e instanceof javax.net.ssl.SSLHandshakeException) {
			Log.i("info","code:证书验证失败");
			mMessage = "证书验证失败";
		} else {
			Log.i("info","code:未知错误");
			mMessage = "未知错误";
		}
		onFailure(mMessage);
	}

	@Override
	public void onComplete() {
		//请求完成，此时做隐藏loading操作
		Log.i("info", "请求回调:onComplete");
		onFinish();
		if (_Disposable != null && _Disposable.isDisposed()) {
			_Disposable.dispose();
		}
	}



	protected  abstract void onFinish();//请求完成hoide loading处理

	protected  abstract void onFailure(String errorMessage);

	protected abstract void onSuccess(T t);


}

