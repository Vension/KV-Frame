package com.kevin.vension.demo.base

import com.kevin.vension.demo.R
import com.kevin.vension.demo.http.agent.OKHttpEngine
import com.kevin.vension.demo.utils.glide.GlideImageLoader
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.vension.frame.VFrame
import com.vension.frame.base.V_Application
import com.vension.frame.utils.AppFrontBackHelper

/**
 * @author ：Created by vension on 2018/2/28.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class KVApplication : V_Application() {

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater { context, layout ->
            //全局设置主题颜色
            layout?.setPrimaryColorsId(R.color.app_main_backgroup_color, android.R.color.white)
            //指定为经典Header，默认是 贝塞尔雷达Header
            MaterialHeader(context)
//            PhoenixHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater { context, _ ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20F)
        }
    }


    override fun onCreate() {
        super.onCreate()
        //初始化多状态界面View
//        VFrame.initXLoadingView().setErrorViewResId(R.layout._loading_layout_error);
        /**
        初始化网络请求的引擎,在这里可以一行代码切换，避免更换网络框架麻烦的问题
        提供三种常见框架的简单案例：（你也可以按照例子自己实现）
        AsyncHttpEngine、OKHttpEngine、VolleyHttpEngine
         */
        VFrame.initXHttp(OKHttpEngine())
        /**
         * 初始化全局图片加载框架
         * GlideImageLoader为你的图片加载框架实现类
         */
        VFrame.initXImageLoader(GlideImageLoader(applicationContext))

        AppFrontBackHelper().register(this, object : AppFrontBackHelper.OnAppStatusListener {
            override fun onFront() {
                //TODO 应用切到前台处理

            }

            override fun onBack() {
                //TODO 应用切到后台处理
            }
        })
    }


}