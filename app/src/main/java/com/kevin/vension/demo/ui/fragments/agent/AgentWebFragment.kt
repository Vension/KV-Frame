package com.kevin.vension.demo.ui.fragments.agent

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseFragment
import com.vension.frame.utils.VStringUtil
import kotlinx.android.synthetic.main.fragment_agent_web.*

/**
 * @author ：Created by vension on 2018/2/11.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class AgentWebFragment : BaseFragment() {


    override fun attachLayoutRes(): Int {
        return R.layout.fragment_agent_web
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
    }

    override fun lazyLoadData() {
    }

    private fun initWebView(url: String, activity: Activity): WebView? {
        return initWebView(url, activity, null, null)
    }

    private fun initWebView(url: String, activity: Activity, listener: onWebProgressListener): WebView? {
        return initWebView(url, activity, null, listener)
    }

    /**
     * 初始化webView
     *
     * @param activity webView窗体
     * @param listener web加载进度监听
     * @param classObj 调用javascript类
     * @return
     */
    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun initWebView(url: String, activity: Activity?, classObj: Any?, listener: onWebProgressListener?): WebView? {
        if (null == activity) {
            return null
        }

        val wv_settings = webView_base.getSettings()
        wv_settings.javaScriptEnabled = true
        wv_settings.defaultTextEncodingName = "UTF-8"
        wv_settings.displayZoomControls = false
        wv_settings.builtInZoomControls = false
        wv_settings.setSupportZoom(false)
        wv_settings.domStorageEnabled = true
        // 加载的页面自适应手机屏幕
        wv_settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS

        webView_base.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //去WebView打开
                view.loadUrl(url)
                return true
            }

        }

        if (null != listener) {
            webView_base.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    if (100 == newProgress) {
                        listener?.onProgressComplete()
                        // 关闭进度条
                        tv_progress.visibility = View.GONE
                    } else {
                        listener?.onProgressStart()
                        // 加载中显示进度条
                        if (View.GONE == tv_progress.visibility) {
                            tv_progress.visibility = View.VISIBLE
                        }
                        tv_progress.progress = newProgress
                    }
                }
            }
        }

        if (null != classObj) {
            webView_base.addJavascriptInterface(classObj, "JsInteface")
        }

        if (!VStringUtil.isEmpty(url)) {
            loadWebPage(webView_base, url)
        }

        return webView_base
    }

    /**
     * 加载网页
     */
    private fun loadWebPage(webView: WebView, params: String) {
        if (this.isUrl(params)) { // url 地址 形式 加载
            //WebView加载web资源
            //          final String urlString = WebPage.IP_PATH
            //                  + this.webPageIdentify
            //                  + this.webPageParams;
            webView.loadUrl(params)
        } else {// 网页 源码 形式 加载
            webView.loadDataWithBaseURL(null, params, "text/html", "utf-8", null)
        }
    }

    private fun isUrl(url: String): Boolean {
        var urlFlag = false
        val strTmp = url.substring(0, 4)
        if (!VStringUtil.isEmpty(url) && !VStringUtil.isEmpty(strTmp)) {
            if ("http" == strTmp || "https" == strTmp) {
                urlFlag = true
            }
        }
        return urlFlag
    }

    /**
     */
    interface onWebProgressListener {
        fun onProgressComplete()
        fun onProgressStart()
    }
}