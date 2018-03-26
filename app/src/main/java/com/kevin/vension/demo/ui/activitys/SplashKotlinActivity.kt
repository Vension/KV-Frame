package com.kevin.vension.demo.ui.activitys

import android.Manifest
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseActivity
import com.kevin.vension.demo.showToast
import com.vension.frame.VFrame
import com.vension.frame.utils.AppUtil
import com.vension.frame.utils.TimeUtil
import kotlinx.android.synthetic.main.activity_splash_kotlin.*

/**
 * @author ：Created by vension on 2018/1/10.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class SplashKotlinActivity : BaseActivity() {

    private var textTypeface: Typeface?=null
    private var descTypeFace: Typeface?=null
    private var alphaAnimation: AlphaAnimation?=null

    init {
        textTypeface = Typeface.createFromAsset(VFrame.getContext().assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(VFrame.getContext().assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun isEnableSlideClose(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_splash_kotlin
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        tv_app_name.typeface = textTypeface
        tv_version.typeface = descTypeFace
        val welcome_hint = getString(R.string.welcome_hint, AppUtil.getVersionName(this), "2014", TimeUtil.getNowString("yyyy"), "kevin~vension.com")
        tv_version.text = welcome_hint

        //渐变展示启动屏
        alphaAnimation= AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                redirectToMain()
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })

        checkPermission()
    }

    override fun requestLoadData() {
    }



    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission(){
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe { granted ->
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                        showToast("用户关闭了权限")
                    }
                    layout_splash.startAnimation(alphaAnimation)
                }
    }

    fun redirectToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
