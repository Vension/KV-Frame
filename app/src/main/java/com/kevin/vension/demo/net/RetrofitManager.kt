package com.kevin.vension.v_kotlinmvp.net

import com.vension.frame.VFrame
import com.vension.frame.utils.NetworkUtil
import com.vension.frame.utils.VPreference
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author ：Created by vension on 2018/1/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 网络请求助手
 */
object RetrofitManager {

    private var _Client : OkHttpClient? = null
    private var _Retrofit : Retrofit? = null

    val _Service : ApiService by lazy { getRetrofit()!!.create(ApiService::class.java)}
    private var token:String by VPreference("token","")


    private fun getRetrofit(): Retrofit? {
         if (_Retrofit == null){
             synchronized(RetrofitManager::class.java){
                 if (_Retrofit == null){
                     //添加一个log拦截器,打印所有的log
                     val httpLoggingInterceptor = HttpLoggingInterceptor()
                     //可以设置请求过滤的水平,body,basic,headers
                     httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                     //设置 请求的缓存的大小跟位置
                     val cacheFile = File(VFrame.getContext().cacheDir, "cache")
                     val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

                     _Client = OkHttpClient.Builder()
                             .addInterceptor(addQueryParameterInterceptor())  //参数添加
                             .addInterceptor(addHeaderInterceptor()) // token过滤
//                            .addInterceptor(addCacheInterceptor())
                             .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                             .cache(cache)  //添加缓存
                             .connectTimeout(60L, TimeUnit.SECONDS)
                             .readTimeout(60L, TimeUnit.SECONDS)
                             .writeTimeout(60L, TimeUnit.SECONDS)
                             .build()

                     // 获取retrofit的实例
                     _Retrofit = Retrofit.Builder()
                             .baseUrl(UriConstant.BASE_URL)  //自己配置
                             .client(_Client!!)
                             .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                             .addConverterFactory(GsonConverterFactory.create())
                             .build()
                 }
             }
         }
        return _Retrofit
    }


    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("phoneSystem", "")
                    .addQueryParameter("phoneModel", "")
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(VFrame.getContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(VFrame.getContext())) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }

}