package com.kevin.vension.demo.ui.activitys

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.WindowManager
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseActivity
import com.kevin.vension.demo.utils.Constant
import com.vension.frame.base.V_BaseFragment
import com.vension.frame.utils.VLogUtil
import com.vension.frame.utils.stack_managers.ActivityStackManager
import java.lang.ref.WeakReference

/**
 * @author ：Created by vension on 2018/2/11.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 代理Activity
 */
class ProxyAvtivity : BaseActivity() {

    private var mFragment: V_BaseFragment? = null

    override fun attachLayoutRes(): Int {
        return R.layout.activity_proxy
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        try {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            // setup main fragment
            val fBundle = intent.extras
            val fFragmentClass = fBundle.getString(Constant.AGENT_FRAGMENT_CLASS_KEY)
            if (!TextUtils.isEmpty(fFragmentClass)) {
                //状态栏是否可见
//                if (!fBundle.containsKey(Constant.AGENT_TOOLBAR_DIAPLAY)) {
//                    fBundle.putBoolean(Constant.AGENT_TOOLBAR_DIAPLAY, true)
//                }
//                //返回按钮是否显示
//                if (!fBundle.containsKey(Constant.AGENT_TOOLBAR_BACK_ENABLE)) {
//                    fBundle.putBoolean(Constant.AGENT_TOOLBAR_BACK_ENABLE, true)
//                }
                mFragment = Class.forName(fFragmentClass).newInstance() as V_BaseFragment
                mFragment?.arguments = fBundle
                mFragment?.let { setMainFragment(it) }
            }
        } catch (e: Exception) {
            ActivityStackManager.getInstance().removeActivity(WeakReference(this))
            finish()
        }


    }

    override fun requestLoadData() {
    }


    private fun setMainFragment(fragment: Fragment) {
        setMainFragment(fragment, 0, 0)
    }


    private fun setMainFragment(fragment: Fragment, enterAnim: Int, exitAnim: Int) {
        if (!this.isFinishing) {
            val mFragmentTransaction = supportFragmentManager.beginTransaction()
            if (enterAnim != 0 && exitAnim != 0) {
                mFragmentTransaction.setCustomAnimations(enterAnim, exitAnim)
            }
            mFragmentTransaction.replace(R.id.agent_content, fragment)
            mFragmentTransaction.commitAllowingStateLoss()
            VLogUtil.e("setMainFragment==>" + fragment)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        if (mFragment != null) mFragment = null
    }

}