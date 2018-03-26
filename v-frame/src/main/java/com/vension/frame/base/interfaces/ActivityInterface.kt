package com.vension.frame.base.interfaces

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment

/**
 * @author ：Created by vension on 2018/2/128.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * BaseActivity 的接口
 */
interface ActivityInterface {

     fun startAgentActivity(_Class: Class<out Fragment>)
     fun startAgentActivity(_Class: Class<out Fragment>, bundle: Bundle)
     fun startAgentActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startAgentActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat)

     fun startAgentActivityForResult(_Class: Class<out Fragment>, requestCode: Int)
     fun startAgentActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int)
     fun startAgentActivityForResult(_Class: Class<out Fragment>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startAgentActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)

}