package com.vension.frame.base

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.gw.swipeback.tools.WxSwipeBackActivityManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.vension.frame.VFrame
import com.vension.frame.imageloader.ImageLoadHelper
import com.vension.frame.utils.FileCache
import kotlin.properties.Delegates

/**
 * @author ：Created by vension on 2018/2/28.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
open class V_Application : MultiDexApplication(){

    private var _RefWatcher : RefWatcher? = null

    companion object{
        private val TAG = "V-Kotlin-MVP"
        var _Context : Context by Delegates.notNull()

        fun getRefWatcher(context : Context) : RefWatcher? {
            val mV_Application = context.applicationContext as V_Application
            return mV_Application._RefWatcher
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        _Context = applicationContext
        _RefWatcher = setupLeakCanary()
        initConfig()//初始化配置
    }

    /**
     * 初始化配置
     */
    private fun initConfig() {
        VFrame.init(this)
        Logger.addLogAdapter(AndroidLogAdapter())//初始化logger
        //侧滑返回初始化
        WxSwipeBackActivityManager.getInstance().init(this)
        // 文件初始化
        FileCache.onInit(this)
        VFrame.initXLog()
        VFrame.initToast()
        VFrame.initCrash()
        ImageLoadHelper.initImageLoader(applicationContext)
    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

}