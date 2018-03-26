package com.kevin.vension.demo.v_custom

import com.kevin.vension.demo.base.BaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * @author ：Created by vension on 2018/3/5.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
abstract class BaseCustomViewFragment : BaseFragment() {

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        val title = mBundle?.getString("custome_title")
        mCommonTitleBar.centerTextView.text = title
    }

}