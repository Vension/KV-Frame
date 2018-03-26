package com.kevin.vension.demo.ui.fragments.agent

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kevin.vension.demo.base.BaseRefreshFragment
import com.kevin.vension.demo.test.BroadCastActivity
import com.kevin.vension.demo.test.CustomViewTestFragment
import com.kevin.vension.demo.test.DialogDemoActivity
import com.kevin.vension.demo.ui.adapters.RecyKnowledgeSummaryAdapter
import com.vension.frame.adapters.recy.decoration.RecGridDividerItemDecoration
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_base_refresh.*

/**
 * @author ：Created by vension on 2018/3/14.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class KnowledgeSummaryFragment : BaseRefreshFragment<String>() {

    private val titles = arrayOf("DialogDemo","BroadCastReceiver","23种设计模式","自定义View学习")

    override fun isShowLoading(): Boolean {
        return false
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.centerTextView.text = "Android基础知识"
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context,3)
    }

    override fun initRefreshFragment() {
        refreshRecyclerView.addItemDecoration(RecGridDividerItemDecoration(createLayoutManager() as GridLayoutManager, 5, true))
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<String, BaseViewHolder>? {
        return RecyKnowledgeSummaryAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<String, BaseViewHolder>) {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as String?
            if (item != null) {
                when (position) {
                    0 -> {
                        startActivity(Intent(activity, DialogDemoActivity::class.java))
                    }
                    1 -> {
                        startActivity(Intent(activity, BroadCastActivity::class.java))
                    }
                    2 -> {
                        mBundle?.putString("web_title", item)
                        mBundle?.putString("web_url", "https://github.com/BeesAndroid/BeesAndroid/blob/master/doc/Android系统设计原则与设计模式.md")
                        startAgentActivity(WebFragment::class.java, mBundle!!)
                    }
                    3 -> {
                        mBundle?.putString("custome_title", item)
                        startAgentActivity(CustomViewTestFragment::class.java,mBundle!!)
                    }
                }
            }
        }
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        addItemData(true, titles.toList())
    }

    override fun hasLoadMore(): Boolean {
        return false
    }

}