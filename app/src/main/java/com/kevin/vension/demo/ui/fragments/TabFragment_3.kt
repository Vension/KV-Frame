package com.kevin.vension.demo.ui.fragments

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
import android.widget.SearchView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseRefreshFragment
import com.kevin.vension.demo.models.TestEntity
import com.kevin.vension.demo.ui.adapters.RecyHotAdapter
import com.kevin.vension.demo.v_custom.DataManager
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.layout_custom_toolbar_hot_center.*
import java.util.*

/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 *  热门控件
 *
 */
class TabFragment_3 : BaseRefreshFragment<TestEntity>() {

    var list = ArrayList<TestEntity>()

    override fun isShowLoading(): Boolean {
        return false
    }
    override fun getToolBarResId(layout: Int): Int {
        return R.layout.layout_toolbar_hot
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        my_searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // 当点击搜索按钮时触发该方法
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            // 当搜索内容改变时触发该方法
            override fun onQueryTextChange(newText: String): Boolean {
                searchDataByKey(newText)
                return false
            }
        })
    }

    /**通过关键字进行筛选*/
    private fun searchDataByKey(extra: String?) {
        if (!TextUtils.isEmpty(extra)) {
            list.clear()
            DataManager.customeTitles
                    .filter { it.title.contains(extra.toString()) }
                    .forEach { list.add(it) }
            addItemData(true,list)
        } else {
            addItemData(true, getListData())
        }
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun initRefreshFragment() {

    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<TestEntity, BaseViewHolder>? {
        return RecyHotAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<TestEntity, BaseViewHolder>) {
        mAdapter.onItemChildClickListener = DataManager.creatItemChildClickListener(this)
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        addItemData(true, getListData())
    }

    override fun hasLoadMore(): Boolean {
        return false
    }

    private fun getListData(): List<TestEntity> {
        list.clear()
        for (i in DataManager.customeTitles.indices) {
            var mEntity = DataManager.customeTitles[i]
            mEntity.id = i + 1
            list.add(mEntity)
        }
        return list
    }


}