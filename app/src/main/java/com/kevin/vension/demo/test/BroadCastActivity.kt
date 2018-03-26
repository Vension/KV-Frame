package com.kevin.vension.demo.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.widget.Toast
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseActivity
import com.vension.frame.VFrame.getSystemService
import com.vension.frame.utils.VToastUtil.showToast
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_broadcast_test.*






/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/23 14:36
 * @author ：Created by vension on 2018/3/12.
 * @email：kevin-vension@foxmail.com
 * ========================================================
 *
 * 描  述：动态注册: 在Java代码中指定IntentFilter，然后想监听什么广播就添加对应的Action。动态注册的广播，一定要调用unregisterReceiver()方法取消注册。
 *        静态注册: 动态注册需要程序启动之后才能接收广播，静态广播就弥补了这个短板。在AndroidManifest中指定IntentReceiver就可以在程序未启动的情况下接收到广播
 *        本地广播：主要是用LocalBroadcastManager来对广播进行管理，提供了发送广播和注册广播接收器的方法如下：
 *                LocalBroadcastManager.getInstance()获得实例
 *                ~.registerReceiver()注册广播
 *                ~.sendBroadcast()发送广播
 *                ~.unregisterReceiver()取消广播
 *         注意事项：本地广播无法通过静态注册来接收，相比系统全局广播更加高效
 *                 在广播中启动活动需要添加FLAG_ACTIVITY_NEW_TASK的标记
 *                 在广播中弹出AlertDialog需要添加TYPE_SYSTEM_ALERT
 *
 * @url 参考自 https://blog.csdn.net/white_idiot/article/details/54862939
 */

class BroadCastActivity : BaseActivity() {

    private var mNetworkChangeReceiver : NetworkChangeReceiver? = null
    private var mLocalReceiver : LocalReceiver? = null
    private var mLocalBroadcastManager : LocalBroadcastManager? = null

    override fun showToolBar(): Boolean {
        return true
    }
    override fun attachLayoutRes(): Int {
        return R.layout.activity_broadcast_test
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.centerTextView.text = "测试广播"
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        /**动态接收网络状态变化的广播接收器*/
        val mIntentFilter = IntentFilter()
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(mNetworkChangeReceiver,mIntentFilter)

        /**发送普通广播*/
        button.setOnClickListener {
            val intent = Intent()
            intent.action = "com.vension.MyReceiver"//设置意图
            intent.putExtra("name", "vension-普通广播")//设置所需发送的消息标签以及内容
            sendBroadcast(intent)//发送普通广播
            Toast.makeText(applicationContext, "发送广播成功", Toast.LENGTH_SHORT).show()
        }

        /**发送有序广播*/
        button2.setOnClickListener {
            val intent = Intent()
            intent.action = "com.mytest"//设置意图
            intent.putExtra("name", "vension-有序广播")//设置所需发送的消息标签以及内容
//            BroadCastActivity.this.sendBroadcast(intent);
            this@BroadCastActivity.sendOrderedBroadcast(intent, null)//有序广播
            Toast.makeText(applicationContext, "发送广播成功", Toast.LENGTH_SHORT).show()
        }

        /**发送本地广播测试*/
        //得到本地广播管理器的实例
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this)
        //动态注册本地广播接收器
        val intentFilter2 = IntentFilter("com.vension.LOCAL_BROADCAST")
        mLocalReceiver = LocalReceiver()
        registerReceiver(mLocalReceiver,intentFilter2)

        button3.setOnClickListener {
            val intent2 = Intent("com.vension.LOCAL_BROADCAST")
            mLocalBroadcastManager?.sendBroadcast(intent2)
        }

    }


    override fun requestLoadData() {
    }


    override fun onDestroy() {
        super.onDestroy()
        //取消动态网络变化广播接收器的注册
        if (mNetworkChangeReceiver != null){
            unregisterReceiver(mNetworkChangeReceiver)
        }
        if (mLocalBroadcastManager != null){
            mLocalBroadcastManager?.unregisterReceiver(mLocalReceiver!!)
        }
    }


    /**自定义网络变化广播接收器*/
    class NetworkChangeReceiver : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            val mConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null && mNetworkInfo.isAvailable){
                showToast("network is available（可用）")
            }else{
                showToast("network is unavailable（不可用）")
            }
        }
    }


    internal inner class LocalReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showToast("接收到本地广播")
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show()
        }
    }

}