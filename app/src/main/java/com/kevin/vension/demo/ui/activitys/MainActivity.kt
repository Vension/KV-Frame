package com.kevin.vension.demo.ui.activitys

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseActivity
import com.kevin.vension.demo.showToast
import com.kevin.vension.demo.ui.fragments.TabFragment_1
import com.kevin.vension.demo.ui.fragments.TabFragment_2
import com.kevin.vension.demo.ui.fragments.TabFragment_3
import com.kevin.vension.demo.ui.fragments.TabFragment_4
import com.vension.frame.utils.AppUtil
import com.vension.frame.utils.VObsoleteUtil
import com.vension.frame.utils.navbar.BottomNavigationViewHelper
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_main.*
import xyz.bboylin.universialtoast.UniversalToast

class MainActivity : BaseActivity() {

    private var _TabFragment_1: TabFragment_1? = null
    private var _TabFragment_2: TabFragment_2? = null
    private var _TabFragment_3: TabFragment_3? = null
    private var _TabFragment_4: TabFragment_4? = null

    //默认为0
    private var mIndex = 2

    override fun isEnableSlideClose(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex",0)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
        outState.putInt("currTabIndex", mIndex)
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }


    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(main_navigation)
        main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        switchFragment(mIndex)
        main_navigation.menu.getItem(mIndex).isChecked = true
        requestPermission()
    }

    override fun requestLoadData() {

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(0)
            }
            R.id.navigation_discover -> {
                switchFragment(1)
            }
            R.id.navigation_hot -> {
                switchFragment(2)
            }
            R.id.navigation_me -> {
                switchFragment(3)
            }
        }
        true
    }


    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
        // 首页
            0 -> _TabFragment_1?.let {
                transaction.show(it)
            } ?: TabFragment_1().let {
                _TabFragment_1 = it
                transaction.add(R.id.main_content, it, "home")
            }
        //发现
            1 -> _TabFragment_2?.let {
                transaction.show(it)
            } ?: TabFragment_2().let {
                _TabFragment_2 = it
                transaction.add(R.id.main_content, it, "discovery") }
        //热门
            2 -> _TabFragment_3?.let {
                transaction.show(it)
            } ?: TabFragment_3().let {
                _TabFragment_3 = it
                transaction.add(R.id.main_content, it, "hot") }
        //我的
            3 -> _TabFragment_4?.let {
                transaction.show(it)
            } ?: TabFragment_4().let {
                _TabFragment_4 = it
                transaction.add(R.id.main_content, it, "mine") }
            else -> {
                transaction.add(R.id.main_content, Fragment(), "agent")
            }
        }
        mIndex = position
        transaction.commitAllowingStateLoss()
    }


    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        _TabFragment_1?.let { transaction.hide(it) }
        _TabFragment_2?.let { transaction.hide(it) }
        _TabFragment_3?.let { transaction.hide(it) }
        _TabFragment_4?.let { transaction.hide(it) }
    }


    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                AppUtil.exit()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private val REQUEST_PERMISSION_CODE = 123
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                UniversalToast.makeText(this, "请允许悬浮窗权限，才能正常使用自定义Toast效果", UniversalToast.LENGTH_SHORT).showWarning()
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName))
                startActivityForResult(intent, REQUEST_PERMISSION_CODE)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == REQUEST_PERMISSION_CODE) {
            val text = if (Settings.canDrawOverlays(this)) "已获取悬浮窗权限" else "请打开悬浮窗权限"
            UniversalToast.makeText(this, text, UniversalToast.LENGTH_SHORT).show()
        }
    }

}
