package com.vension.frame.base

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.FrameLayout
import com.tbruyelle.rxpermissions2.RxPermissions
import com.vension.frame.R
import com.vension.frame.base.interfaces.FragmentInterface
import com.vension.frame.observers.FragmentObserver
import com.vension.frame.utils.NetworkUtil
import com.vension.frame.utils.VLogUtil
import com.vension.frame.views.MultipleStatusView
import com.wuhenzhizao.titlebar.widget.CommonTitleBar


/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * Fragment 基类
 */
abstract class V_BaseFragment : Fragment(),FragmentInterface {

    var rootView: View? = null //在使用自定义toolbar时候的根布局 = toolBarView+childView
    protected var mLayoutStatusView: MultipleStatusView? = null //多种状态的 View 的切换
    protected var mCommonTitleBar: CommonTitleBar? = null //多种状态的 View 的切换
    private lateinit var mRxPermissions: RxPermissions //6.0动态获取权限
    protected var mBundle: Bundle? = null
    private var isViewPrepare = false //视图是否加载完毕
    private var hasLoadData = false   //是否加载过数据

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VLogUtil.e("V_BaseFragment onCreate")
        mBundle = this.arguments
        if (mBundle == null) {
            mBundle = Bundle()
        }
        mRxPermissions = RxPermissions(context as Activity)
        FragmentObserver.getInstance().regist(this)
//        FragmentStackManager.getInstance().addFragment(WeakReference(this))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            //为空时初始化。
            if (showToolBar() && getToolBarResId() != -1) {
                //如果需要显示自定义toolbar,并且资源id存在的情况下，实例化baseView;
                rootView = inflater?.inflate(if (isToolbarCover())
                    R.layout.activity_base_toolbar_cover
                else
                    R.layout.activity_base, container, false)//根布局
                val vs_toolbar = rootView?.findViewById<View>(R.id.vs_toolbar) as ViewStub//toolbar容器
                val fl_container = rootView?.findViewById<View>(R.id.fl_container) as FrameLayout//子布局容器
                vs_toolbar.layoutResource = getToolBarResId()//toolbar资源id
                vs_toolbar.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                vs_toolbar.inflate()//填充toolbar
                inflater?.inflate(attachLayoutRes(), fl_container, true)//子布局
            } else {
                //不显示通用toolbar
                rootView = inflater?.inflate(attachLayoutRes(), container, false)
            }
        }
        VLogUtil.e("V_BaseFragment onCreateView")
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        VLogUtil.e("V_BaseFragment onViewCreated")
        isViewPrepare = true
        if (showToolBar()){
            mCommonTitleBar = rootView?.findViewById<View>(R.id.mCommonTitleBar) as CommonTitleBar//子布局容器
            initToolBar(mCommonTitleBar!!)//初始化标题栏
        }

        initViewAndData(savedInstanceState)
        //多种状态切换的view 重试点击事件
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userVisibleHint = true//fragment 可见
        VLogUtil.e("V_BaseFragment onActivityCreated")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //如果fragment可见进行懒加载数据
        VLogUtil.e("V_BaseFragment setUserVisibleHint")
        if (isVisibleToUser){
            VLogUtil.e("V_BaseFragment isVisibleToUser")
            initRequestState()//初始化状态
        }
    }

    /**初始化状态*/
    private fun initRequestState() {
        if (isShowLoading()){
            mLayoutStatusView?.showLoading()
        }
        Handler().postDelayed({
            if (isCheckNet()){
                if (!NetworkUtil.isConnected(activity)){
                    mLayoutStatusView?.showNoNetwork()
                    return@postDelayed
                }
            }
            lazyLoadDataIfPrepared()//请求网络数据
        },300)
    }

    /**
     *  页面初始化完进行懒加载
     **/
    private fun lazyLoadDataIfPrepared() {
        VLogUtil.e("V_BaseFragment lazyLoadDataIfPrepared")
        if (userVisibleHint && isViewPrepare && !hasLoadData){
            lazyLoadData()
            hasLoadData = true
            VLogUtil.e("V_BaseFragment lazyLoadData")
        }
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
    abstract fun lazyLoadData()

    // ====================================================================
    /**
     * 是否显示通用toolBar
     *
     * @return true 默认显示
     */
    open fun showToolBar(): Boolean {
        return true
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
     fun getToolBarResId(): Int {
        return getToolBarResId(0)
    }
     open fun getToolBarResId(layout : Int): Int {
        return if (layout > 0) layout else R.layout.v_layout_default_toolbar
    }
    /**
     * 请求数据前是否显示loading页面
     *
     * @return true 默认显示
     */
    open fun isShowLoading(): Boolean {
        return true
    }
    /**
     * 请求数据前是否检查网络连接
     *
     * @return false 默认不检查
     */
    open fun isCheckNet(): Boolean {
        return false
    }

    // ====================================================================



    override fun onDestroy() {
        super.onDestroy()
        FragmentObserver.getInstance().unregist(this)
//        FragmentStackManager.getInstance().removeFragment(WeakReference(this))
        V_Application.getRefWatcher(activity!!)?.watch(activity)
    }


}