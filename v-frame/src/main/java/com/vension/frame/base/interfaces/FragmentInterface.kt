package com.vension.frame.base.interfaces

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat

/**
 * @author ：Created by vension on 2018/2/11.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
interface FragmentInterface {

     fun startAgentActivity(_Class: Class<*>)
     fun startAgentActivity(_Class: Class<*>, bundle: Bundle)

     fun startAgentActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startAgentActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat)

     fun startAgentActivityForResult(_Class: Class<*>, requestCode: Int)
     fun startAgentActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int)
     fun startAgentActivityForResult(_Class: Class<*>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startAgentActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)

}