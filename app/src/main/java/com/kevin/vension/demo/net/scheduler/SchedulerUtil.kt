package com.kevin.vension.v_kotlinmvp.net.scheduler

import com.hazz.kotlinmvp.rx.scheduler.IoMainScheduler

/**
 * @author ：Created by vension on 2018/1/23.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
object SchedulerUtil {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }

}