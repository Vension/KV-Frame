package com.vension.frame.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import com.gw.swipeback.WxSwipeBackLayout
import com.tbruyelle.rxpermissions2.RxPermissions
import com.vension.frame.R
import com.vension.frame.base.interfaces.ActivityInterface
import com.vension.frame.observers.ActivityObserver
import com.vension.frame.utils.NetworkUtil
import com.vension.frame.views.MultipleStatusView
import com.wuhenzhizao.titlebar.utils.AppUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * @author ：Created by vension on 2018/2/28.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
abstract class V_BaseActivity : AppCompatActivity(), ActivityInterface {
    var mContext: Context? = null
    var rootView: View? = null //在使用自定义toolbar时候的根布局 = toolBarView+childView
    protected var mLayoutStatusView: MultipleStatusView? = null //多种状态的 View 的切换
    lateinit var mRxPermissions: RxPermissions //6.0动态获取权限


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        KeyboardConflictCompat.assistWindow(window)//
        AppUtils.transparencyBar(window)//透明状态栏
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(attachLayoutRes())//加载布局
            mContext = this.applicationContext
            mRxPermissions = RxPermissions(this)

            //设置侧滑返回
            if (isEnableSlideClose()) {
                val mSwipeBackLayout = WxSwipeBackLayout(this)
                mSwipeBackLayout.maskAlpha = 180
                mSwipeBackLayout.attachToActivity(this)
            }

            //设置ToolBar
            if (showToolBar()) {
                var mCommonTitleBar = rootView?.findViewById<View>(R.id.mCommonTitleBar) as CommonTitleBar//子布局容器
                initToolBar(mCommonTitleBar)//初始化标题栏
            }

            initViewAndData(savedInstanceState)//初始化view和数据
            initRequestState()//初始化状态
            setRetryClickListener()//设置重新加载按钮点击监听
            ActivityObserver.getInstance().regist(this)//activity进栈
//            ActivityStackManager.getInstance().addActivity(WeakReference(this))//activity进栈

        }catch (ex : Exception){
            ex.printStackTrace()
        }
    }


    override fun setContentView(@LayoutRes layoutResID: Int) {
        if (layoutResID == 0) {
            throw RuntimeException("layoutResID == -1 have u create your layout?")
        }
        if (showToolBar() && getToolBarResId() != -1) {
            //如果需要显示自定义toolbar,并且资源id存在的情况下，实例化baseView;
            rootView = LayoutInflater.from(this).inflate(if (isToolbarCover())
                R.layout.activity_base_toolbar_cover
            else
                R.layout.activity_base, null, false)//根布局
            val vs_toolbar = rootView?.findViewById<View>(R.id.vs_toolbar) as ViewStub//toolbar容器
            val fl_container = rootView?.findViewById<View>(R.id.fl_container) as FrameLayout//子布局容器
            vs_toolbar.layoutResource = getToolBarResId()//toolbar资源id
            vs_toolbar.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            vs_toolbar.inflate()//填充toolbar
            LayoutInflater.from(this).inflate(layoutResID, fl_container, true)//子布局
            setContentView(rootView)
        } else {
            //不显示通用toolbar
            super.setContentView(layoutResID)
        }

    }

    /**初始化状态*/
    private fun initRequestState() {
        if (isShowLoading()) {
            mLayoutStatusView?.showLoading()
        }
        Handler().postDelayed({
            if (isCheckNet()) {
                if (!NetworkUtil.isConnected(mContext)) {
                    mLayoutStatusView?.showNoNetwork()
                    return@postDelayed
                }
            }
            requestLoadData()//请求网络数据
        }, 300)
    }


    /**设置重新加载按钮点击监听*/
    private fun setRetryClickListener() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        initRequestState()
    }


    /**
     *  加载布局
     */
    @LayoutRes
    abstract fun attachLayoutRes(): Int

    /**
     * 初始化标题栏
     */
    abstract fun initToolBar(mCommonTitleBar: CommonTitleBar)

    /**
     *  请求数据前的一些初始化设置
     */
    abstract fun initViewAndData(savedInstanceState: Bundle?)

    /**
     * 请求加载网络数据
     */
    abstract fun requestLoadData()


    // ====================================================================
    /**
     * 是否显示通用toolBar 默认不显示
     *
     * @return false
     */
    open fun showToolBar(): Boolean {
        return false
    }

    /**
     * toolbar是否覆盖在内容区上方
     *
     * @return false 不覆盖  true 覆盖
     */
    open fun isToolbarCover(): Boolean {
        return false
    }

    /**
     * 获取自定义toolbarview 资源id 默认为-1，
     * showToolBar()方法必须返回true才有效
     */
    private fun getToolBarResId(): Int {
        return R.layout.v_layout_default_toolbar
    }

    /**
     * 请求数据前是否显示loading页面
     *
     * @return true 默认显示
     */
    open fun isShowLoading(): Boolean {
        return false
    }

    /**
     * 请求数据前是否检查网络连接
     *
     * @return false 默认不检查
     */
    open fun isCheckNet(): Boolean {
        return false
    }

    /**
     * 是否开启侧滑返回
     *
     * @return true 默认开启
     */
    open fun isEnableSlideClose(): Boolean {
        return true
    }
    // ====================================================================

    //重写此方法，会在内存紧张的时候回调（支持多个级别），便于我们主动的进行资源释放，避免OOM。
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onDestroy() {
        super.onDestroy()
        rootView = null
        ActivityObserver.getInstance().unregist(this)//activity出栈
//        ActivityStackManager.getInstance().removeActivity(WeakReference(this))
        V_Application.getRefWatcher(this)?.watch(this)
    }

    override fun onBackPressed() {
        // Fragment 逐个出栈
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }


    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }


    // ====================================添加fragment=======================================
    /**
     * 添加 Fragment
     * @param containerViewId
     * @param fragment
     */
    protected fun addFragment(containerViewId: Int, fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment)
        fragmentTransaction.commit()
    }


    /**
     * 添加 Fragment
     * @param containerViewId
     * @param fragment
     */
    protected fun addFragment(containerViewId: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.add(containerViewId, fragment, tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }

    /**
     * 替换 Fragment
     * @param containerViewId
     * @param fragment
     */
    protected fun replaceFragment(containerViewId: Int, fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerViewId, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /**
     * 替换 Fragment
     * @param containerViewId
     * @param fragment
     */
    protected fun replaceFragment(containerViewId: Int, fragment: Fragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag)
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag)
            fragmentTransaction.commit()
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            supportFragmentManager.popBackStack(tag, 0)
        }
    }

}