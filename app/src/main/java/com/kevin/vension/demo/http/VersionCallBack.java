package com.kevin.vension.demo.http;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 版本更新回调
 */
public abstract class VersionCallBack<T> implements Observer<T> {
	//onSubscribe()：RxJava 2.0 中新增的，传递参数为Disposable ，Disposable 相当于RxJava1.x中的Subscription,用于解除订阅。
	Disposable _Disposable;
	@Override
	public void onSubscribe(@NonNull Disposable d) {
		this._Disposable = d;
	}

	@Override
	public void onNext(@NonNull T result) {
		onSuccess(result);
	}

	@Override
	public void onError(Throwable e) {
		// 连接错误才执行
		String mMessage = "";
		if (e instanceof HttpException) {
			HttpException mHttpException = (HttpException) e;
			switch (mHttpException.code()) {
				case 504:
					mMessage = "网络中断，请检查您的网络状态";
					break;
			}
		} else if (e instanceof NetworkErrorException){
			mMessage = "网络未连接，请检查您的网络状态";
		}else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
			mMessage = "连接超时";
		} else if (e instanceof ConnectException) {
			mMessage = "连接错误";
		} else {
			mMessage = "连接未知异常";
		}
		this.onComplete();
		_Disposable.dispose();
		onFailure(mMessage);
	}


	@Override
	public void onComplete() {
		//请求完成，此时做隐藏loading操作
		Log.i("info", "请求回调:onComplete");
		if (_Disposable != null && _Disposable.isDisposed()) {
			_Disposable.dispose();
		}
	}

	protected  abstract void onFailure(String errorMessage);

	protected abstract void onSuccess(T t);

}
