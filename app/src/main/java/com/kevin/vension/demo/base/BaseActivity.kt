package com.kevin.vension.demo.base

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.kevin.vension.demo.utils.AppCompat
import com.vension.frame.base.V_BaseActivity
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * @author ：Created by vension on 2018/2/11.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
abstract class BaseActivity : V_BaseActivity(), View.OnClickListener{

    private var _Unbinder: Unbinder? = null

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setListener {v: View?, action: Int, extra: String? ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON || action == CommonTitleBar.ACTION_LEFT_TEXT) {
              onBackPressed()
           }
        }
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        _Unbinder = ButterKnife.bind(this)
    }

    override fun startAgentActivity(_Class: Class<out Fragment>) {
        startActivity(AppCompat.createAgentIntent(this, _Class))
    }

    override fun startAgentActivity(_Class: Class<out Fragment>, bundle: Bundle) {
        startActivity(AppCompat.createAgentIntent(this, _Class, bundle))
    }

    override fun startAgentActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivity(this, AppCompat.createAgentIntent(this, _Class), _ActivityOptionsCompat.toBundle())
    }

    /**
     *  val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, iv_search, iv_search.transitionName)
     *  startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
     **/
    override fun startAgentActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivity(this, AppCompat.createAgentIntent(this, _Class, bundle), _ActivityOptionsCompat.toBundle())
    }

    override fun startAgentActivityForResult(_Class: Class<out Fragment>, requestCode: Int) {
        startActivityForResult(AppCompat.createAgentIntent(this, _Class), requestCode)
    }

    override fun startAgentActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int) {
        startActivityForResult(AppCompat.createAgentIntent(this, _Class, bundle), requestCode)
    }

    override fun startAgentActivityForResult(_Class: Class<out Fragment>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivityForResult(this, AppCompat.createAgentIntent(this, _Class), requestCode, _ActivityOptionsCompat.toBundle())
    }

    override fun startAgentActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivityForResult(this, AppCompat.createAgentIntent(this, _Class, bundle), requestCode, _ActivityOptionsCompat.toBundle())
    }

    override fun onClick(p0: View?) {
        //TODO 点击事件监听
    }


     override fun onDestroy() {
        super.onDestroy()
        _Unbinder?.unbind()
    }

}