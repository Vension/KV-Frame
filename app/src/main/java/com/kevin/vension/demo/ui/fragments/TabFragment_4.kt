package com.kevin.vension.demo.ui.fragments

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseFragment
import com.kevin.vension.demo.showToast
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_tab_4.*

/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class TabFragment_4 : BaseFragment() {

    override fun getToolBarResId(layout: Int): Int {
        return R.layout.layout_toolbar_me
    }
    override fun attachLayoutRes(): Int {
         return R.layout.fragment_tab_4
    }

    override fun isToolbarCover(): Boolean {
        return true
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.alpha = 0.0f
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)

        myNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val height = diagonalLayout.height
            var alpha = 1.0f
            if (mCommonTitleBar?.top!! + scrollY < height) {
                alpha = scrollY / (height - mCommonTitleBar?.height!!).toFloat()
            }
            mCommonTitleBar?.alpha = alpha
        })
        cardView_user.setOnClickListener(this)
        view_my_money_package.setOnClickListener(this)
        view_my_order.setOnClickListener(this)
        view_shopping_cart.setOnClickListener(this)
        view_my_collection.setOnClickListener(this)
        view_my_focus.setOnClickListener(this)
        view_my_message.setOnClickListener(this)
    }

    override fun lazyLoadData() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.cardView_user-> showToast("个人信息")
            R.id.view_my_money_package-> showToast("我的钱包")
            R.id.view_my_order-> showToast("我的订单")
            R.id.view_shopping_cart-> showToast("我的购物车")
            R.id.view_my_collection-> showToast("我的收藏")
            R.id.view_my_focus-> showToast("我的关注")
            R.id.view_my_message-> showToast("我的消息")
        }
    }

}