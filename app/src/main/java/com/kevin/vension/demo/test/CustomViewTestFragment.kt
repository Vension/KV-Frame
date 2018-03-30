package com.kevin.vension.demo.test

import android.os.Bundle
import com.kevin.vension.demo.R
import com.kevin.vension.demo.test.datas.PieData
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment
import kotlinx.android.synthetic.main.fragment_test_study_customview.*
import java.util.*

/**
 * @author ：Created by Vension on 2018/3/23 15:53.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class CustomViewTestFragment : BaseCustomViewFragment() {

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_test_study_customview
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)

        val datas = ArrayList<PieData>()
        val pieData = PieData("sloop", 60f)
        val pieData2 = PieData("sloop", 30f)
        val pieData3 = PieData("sloop", 40f)
        val pieData4 = PieData("sloop", 20f)
        val pieData5 = PieData("sloop", 20F)
        datas.add(pieData)
        datas.add(pieData2)
        datas.add(pieData3)
        datas.add(pieData4)
        datas.add(pieData5)
        my_pieview.setData(datas)
    }
    override fun lazyLoadData() {
    }
}