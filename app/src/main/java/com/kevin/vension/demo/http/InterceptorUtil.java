package com.kevin.vension.demo.http;

import android.util.Log;

import com.vension.frame.VFrame;
import com.vension.frame.utils.NetworkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2017/5/3.
 * @author Vension
 * @date 2017/5/3
 * @description 拦截器工具类!
 */

public class InterceptorUtil {


	/**日志拦截器*/
	public static Interceptor initLogInterceptor(){
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(String message) {
				Log.i("info", "请求参数:" + message);
			}
		});
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
		return  interceptor;
	}

	/**
	 * 网络拦截, 对(请求)和(结果)操作
	 * 添加公共的头部headers
	 */

	public static Interceptor HeaderParamInterceptor() {
		return new Interceptor(){
			@Override
			public Response intercept(Chain chain) throws IOException {
				//拿到请求体
				Request request = chain.request();
				// 获取Token时需要旧的Token
//				if(!TextUtils.isEmpty(request.header("Authorization"))) {
//					return chain.proceed(request);
//				}

//				String _Url = request.url().encodedPath();
//				String _TimeStamp = String.valueOf(System.currentTimeMillis());
//				String _Authorization = UserACache.getInstance().getAccessToken();
//				LogUtil.e("_Authorization==》" + _Authorization);
//				String _SubAuthorization = _Authorization.substring(8, 31);
//				String _SignedString = _Url + _TimeStamp + "KWMV2" + _SubAuthorization;
				// 添加头部参数
				request = request.newBuilder()
//						.header("Content-Type","application/json; charset=UTF-8")
//						.header("Connection", "keep-alive")
//						.header("Accept", "*/*")
//						.header("Cache-Control", String.format("public, max-age=%d", 60))
//						.header("Authorization", _Authorization)
//						.header("X-Oc-TimeStamp", _TimeStamp)
//						.header("X-Oc-Device-Model",  android.os.Build.MODEL)
//						.header("X-Oc-Os-Model","Android " + android.os.Build.VERSION.RELEASE)
//						.header("X-Oc-App-Bundle", VersionUtil.getPackageName(MTApplication.getContext()))
//						.header("X-Oc-App-Version", VersionUtil.getVersionName(MTApplication.getContext()))
//						.header("X-Oc-Merchant-Language", "2")
//						.header("X-Oc-Sign", MD5Helper.MD5(_SignedString).toString().toLowerCase())
						.build();
				return chain.proceed(request);
			}
		};
	}


	/**
	 * 云端响应头拦截器，用来配置缓存策略
	 * Dangerous interceptor that rewrites the server's cache-control header.
	 */
	public static Interceptor CacheInterceptor() {
		return new Interceptor(){
			 @Override
			 public Response intercept(Chain chain) throws IOException {
				 CacheControl.Builder cacheBuilder = new CacheControl.Builder();
				 cacheBuilder.maxAge(0, TimeUnit.SECONDS);
				 cacheBuilder.maxStale(7, TimeUnit.DAYS);//设缓存有效期为7天
				 CacheControl cacheControl = cacheBuilder.build();

				 Request request = chain.request();
				 if (!NetworkUtil.isNetworkAvailable(VFrame.getContext())) {
					 request = request.newBuilder().cacheControl(cacheControl).build();
				 }

				 Response originalResponse = chain.proceed(request);
				 if (NetworkUtil.isNetworkAvailable(VFrame.getContext())) {
					 //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
					 int maxAge = 0; // read from cache
					 return originalResponse.newBuilder()
							 .removeHeader("Pragma")
							 //查询网络的Cache-Control设置
							 //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
							 .header("Cache-Control", "public ,max-age=" + maxAge)
							 .build();
				 } else {
					 int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
					 return originalResponse.newBuilder()
							 .removeHeader("Pragma")
							 .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
							 .build();
				 }
			 }
		 };
	}



//	/**
//	 * token验证的拦截器
//	 * @return
//	 */
//	public static Interceptor tokenInterceptor() {
//		return new Interceptor() {
//			@Override
//			public Response intercept(Chain chain) throws IOException {
//				/**
//				 * 1.拦截到返回的数据
//				 * 2.判断token是否失效
//				 * 3.失效获取新的token
//				 * 4.重新请求接口
//				 */
//
//				//拿到请求体,并添加header携带上token
//				Request mRequest = chain.request().newBuilder()
//						.addHeader("Token", Token)
//						.build();
//				//拿到响应体
//				Response mResponse = chain.proceed(mRequest);
//				ResponseBody responseBody = mResponse.body();
//
//				//得到缓冲源
//				BufferedSource source = responseBody.source();
//
//				//请求全部
//				source.request(Long.MAX_VALUE); // Buffer the entire body.
//				Buffer buffer = source.buffer();
//				Charset charset = UTF8;
//
//				MediaType contentType = responseBody.contentType();
//
//				if (contentType != null) {
//					charset = contentType.charset(UTF8);
//				}
//				//读取返回数据
//				String bodyString = buffer.clone().readString(charset);
//				if (bodyString != null) {
//					//处理返回的数据我这创建了一个BaseEntity来将数据转化为对象
//					MyHttpResponse bean = JSON.parseObject(bodyString, MyHttpResponse.class);
//					//假设当返回的code为42444时token失效
//					if (bean.getCode() == Api.Code.TOKEN_INVALID) {
//						//重新获取新token
//						//这用了一个特殊接口来获取新的Token
//						getToken(new cn.miaotalk.app.http.HttpReqCallBack<TokenEntity>() {
//							@Override
//							public void onResult(boolean isSuccess, String errorMessage, MyHttpResponse<TokenEntity> result) {
//								if (isSuccess){
//									UserACache.getInstance().setToken(result.getData());
//								}
//							}
//						});
//						Call<String> call = RetrofitHttpHelper.getInstance().getToken(new HttpReqCallBack<Object>() {
//						});
//						//拿到请求体
//						Request tokenRequest = call.request();
//						//获取响应体
//						Response tokenResponse = chain.proceed(tokenRequest);
//						//我这假设新的token是在header里返回的
//						//我们拿到新的token头
//						List<String> listHeader = tokenResponse.headers().values("Token");
//						if (listHeader != null) {
//							//重新赋值到新的token
//							Token = listHeader.get(0);
//						}
//
//						//这是只需要替换掉之前的token重新请求接口就行了
//						Request newRequest = mRequest.newBuilder()
//								.header("Token", Token)
//								.build();
//						return chain.proceed(newRequest);
//					}
//				}
//
//				return chain.proceed(mRequest);
//			}
//		};
//
//	}


}
