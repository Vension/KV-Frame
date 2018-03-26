package com.kevin.vension.demo.base

/**
 * @author ：Created by vension on 2018/1/10.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * @use 使用 in 符号修饰参数类型，表示只能被用作方法的参数，被操作，而不能作为返回值使用： <in T>
 */
interface V_IPresenter<in V : V_IBaseView> {

    fun attachView(mRootView : V)

    fun detachView()

}