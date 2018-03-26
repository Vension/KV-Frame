package com.kevin.vension.demo.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseFragment
import com.vension.frame.utils.VObsoleteUtil
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_web.*

/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class TabFragment_2 : BaseFragment() {

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
        mCommonTitleBar.leftImageButton.visibility = View.GONE
        mCommonTitleBar.centerTextView.text = "优秀项目收集"
        mCommonTitleBar.centerTextView.setTextColor(VObsoleteUtil.getColor(R.color.white))
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_web
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        val url = "https://github.com/Vension/V_GoodWheelLib"
        //禁止跳转到第三方浏览器
        webview.webViewClient = WebViewClient()
        //允许JS互调。可以使用网页上的功能代码了
        webview.settings.javaScriptEnabled = true
        //可以设置进度条和页面进度
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progress_bar.visibility = View.GONE
                } else {
                    progress_bar.visibility = View.VISIBLE
                    progress_bar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }
        webview.loadUrl(url)
    }

    override fun lazyLoadData() {
    }


}