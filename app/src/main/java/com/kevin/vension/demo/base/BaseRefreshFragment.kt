package com.kevin.vension.demo.base

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kevin.vension.demo.R
import com.kevin.vension.demo.showToast
import com.vension.frame.utils.NetworkUtil
import com.vension.frame.utils.VLogUtil
import kotlinx.android.synthetic.main.fragment_base_refresh.*

/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
abstract class BaseRefreshFragment<T> : BaseFragment(), SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private var mAdapter: BaseQuickAdapter<T,BaseViewHolder>? = null
    private var page: Int = 1 //页数
    private val limit: Int = 15 //条目数

    override fun attachLayoutRes(): Int {
        VLogUtil.e("attachLayoutRes")
        return R.layout.fragment_base_refresh
    }

    init {
        //TODO 一些初始化
        VLogUtil.e("init")
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        VLogUtil.e("initViewAndData")
        mLayoutStatusView = refresh_MultipleStatusView
        //初始化刷新控件
        mRefreshLayout.setColorSchemeColors(Color.rgb(76, 141, 210))
        //设置刷新控件监听
        mRefreshLayout.setOnRefreshListener(this)
        //初始化_RecyclerView
        refreshRecyclerView.setHasFixedSize(true)
        refreshRecyclerView.layoutManager = createLayoutManager()
        refreshRecyclerView.itemAnimator = DefaultItemAnimator()

        //一些特殊的初始化
        initRefreshFragment()

        mAdapter = createCommonRecyAdapter()
        mAdapter.let {
            mAdapter?.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
            if (hasLoadMore()) {
                mAdapter?.setOnLoadMoreListener(this, refreshRecyclerView)
            }
            addItemClickListener(it!!)
            refreshRecyclerView.adapter = mAdapter
        }

    }


    /**加载数据*/
    override fun lazyLoadData() {
        //自动刷新，演示效果
        VLogUtil.e("lazyLoadData")
        mRefreshLayout.isRefreshing = true
        mRefreshLayout.postDelayed( {
            onRefresh()
        },300)
    }

    /**下拉刷新数据*/
    override fun onRefresh() {
        VLogUtil.e("onRefresh")
        if (isCheckNet()){
            if (!NetworkUtil.isConnected(activity)){
                refresh_MultipleStatusView?.showNoNetwork()
                if (mRefreshLayout.isRefreshing) {
                    mRefreshLayout.isRefreshing = false
                }
                return
            }
        }
        mAdapter?.setEnableLoadMore(false)//这里的作用是防止下拉刷新的时候还可以上拉加载
        onTargerRequestApi(true,1,limit)
    }


    /**加载更多*/
    override fun onLoadMoreRequested() {
        //上拉加载更多/下一页
        VLogUtil.e("onLoadmore")
        mRefreshLayout?.isEnabled = false //加载更多时不能同时下拉刷新
        onTargerRequestApi(false, ++page, limit)
    }


    /** ================================= 设置数据 Start ============================ */

    /**
     * 设置数据
     *
     * @param isRefresh 是否刷新
     * @param listData  列表数据
     */
    fun addItemData(isRefresh : Boolean,listData : List<T>) {
        addItemData(isRefresh,listData, null, null)
    }

    /**
     * 设置数据
     *
     * @param isRefresh 是否刷新
     * @param listData  列表数据
     * @param header    头部
     * @param footer    尾部
     * */
    fun addItemData(isRefresh: Boolean, listData: List<T>, header: List<View>?, footer: List<View>?) {
        VLogUtil.e("addItemData")
         mAdapter.let {
             if (isRefresh){
                 VLogUtil.e("isRefresh")
                 //加载第一页数据
                 refresh_MultipleStatusView.showContent()
                 mAdapter?.setEnableLoadMore(true)
                 mAdapter?.removeAllHeaderView()
                 mAdapter?.removeAllFooterView()
                 //添加头部
                if (header != null && header.isNotEmpty()){
                    for (view in header) {
                        mAdapter?.addHeaderView(view)
                    }
                }
                 //添加尾部
                 if (footer != null && footer.isNotEmpty()){
                     for (view in footer) {
                         mAdapter?.addFooterView(view)
                     }
                 }
                 //添加内容
                 if (listData.isNotEmpty()) {
                     mAdapter?.setNewData(listData)
                 } else {
                     refresh_MultipleStatusView.showEmpty()
                 }
             }
             else //加载下一页数据
             {
                 VLogUtil.e("加载下一页数据")
                 mRefreshLayout.isEnabled = true //加载更多时不能同时下拉刷新
                 if (listData.isNotEmpty()) {
                     VLogUtil.e("mAdapter.addItemData")
                     mAdapter?.addData(listData)
                     mAdapter?.loadMoreComplete()//加载更多完成
                 }else{
                     showToast("没有更多了")
                     mAdapter?.loadMoreEnd()//没有更多了
                 }
             }
         }
        if (mRefreshLayout.isRefreshing) {
            mRefreshLayout.isRefreshing = false
        }
    }


    /** ================================= 设置数据 End ============================ */

    /**一些特殊处理*/
    abstract fun initRefreshFragment()
    /**创建adapter*/
    abstract fun createCommonRecyAdapter(): BaseQuickAdapter<T, BaseViewHolder>?
    /**adapter 的点击事件处理*/
    abstract fun addItemClickListener(mAdapter: BaseQuickAdapter<T, BaseViewHolder>)
    /**请求Api数据*/
    abstract fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int)

    /**
     * 是否显示通用toolBar 默认不显示
     *
     * @return false
     */
    open fun hasLoadMore(): Boolean {
        VLogUtil.e("hasLoadMore")
        return true
    }
    /**
     * 创建RecyclerView 的 LayoutManager， 默认LinearLayoutManager
     * @return  LinearLayoutManager
     */
    open fun createLayoutManager(): RecyclerView.LayoutManager {
        VLogUtil.e("createLayoutManager")
        return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }



open fun showError(errorMsg: String, errorCode: Int) {
    VLogUtil.e("showError")
    showToast(errorMsg)
    mRefreshLayout.isRefreshing = true
    if (page == 1) {
        refresh_MultipleStatusView?.showError()
    } else {
        mAdapter?.loadMoreFail()
    }
}


}

