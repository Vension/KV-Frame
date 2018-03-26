package com.kevin.vension.demo.test

import com.kevin.vension.demo.R
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment

/**
 * @author ：Created by Vension on 2018/3/23 15:53.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class CustomViewTestFragment : BaseCustomViewFragment() {

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_test_study_customview
    }

    override fun lazyLoadData() {
    }
}