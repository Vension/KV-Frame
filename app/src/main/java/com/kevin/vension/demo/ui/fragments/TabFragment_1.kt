package com.kevin.vension.demo.ui.fragments

import android.os.Handler
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseRefreshFragment
import com.kevin.vension.demo.models.TestEntity
import com.kevin.vension.demo.showToast
import com.kevin.vension.demo.ui.adapters.RecyHomeAdapter
import com.kevin.vension.demo.ui.fragments.agent.SearchFilterFragment
import com.vension.frame.VFrame
import com.vension.frame.adapters.recy.decoration.RecHorizontalDividerItemDecoration
import com.vension.frame.views.flypage_indicaor.FlycoPageIndicaor
import com.vension.frame.views.grid_viewpager.GridViewPager
import com.vension.frame.views.grid_viewpager.SortModel
import com.vension.frame.views.marqueeView.MarqueeVerticalTextView
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_base_refresh.*
import kotlinx.android.synthetic.main.layout_custom_toolbar_left.*
import kotlinx.android.synthetic.main.layout_custom_toolbar_right.*
import java.util.*

/**
 * @author ：Created by vension on 2018/3/1.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class TabFragment_1 : BaseRefreshFragment<TestEntity>() {

    override fun isShowLoading(): Boolean {
        return false
    }

    override fun getToolBarResId(layout: Int): Int {
        return R.layout.layout_toolbar_home
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        tv_currentCity.setOnClickListener {
            showToast("广州")
        }
        toolbar_home_scan.setOnClickListener {
            showToast("扫码")
        }
        mCommonTitleBar.setListener { v, action, extra ->
            when(action){
                CommonTitleBar.ACTION_SEARCH_SUBMIT ->{
                    showToast(extra)
                    mBundle?.putString("search_key", "extra")
                    startAgentActivity(SearchFilterFragment::class.java,mBundle!!)
                }
                CommonTitleBar.ACTION_SEARCH_VOICE ->{
                    showToast("请说话")
                }
            }
        }
    }

    override fun initRefreshFragment() {
        refreshRecyclerView.addItemDecoration(RecHorizontalDividerItemDecoration.Builder(activity).build())
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<TestEntity, BaseViewHolder>? {
        return RecyHomeAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<TestEntity, BaseViewHolder>) {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            var item : TestEntity = adapter.getItem(position) as TestEntity
            item.let {
                showToast(it.title)
            }
        }
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        addItemData(true, getListData(),getHeaderView(),getFooterView())
    }


    override fun hasLoadMore(): Boolean {
        return false
    }


    private fun getListData(): List<TestEntity> {
        return (0..19).map { TestEntity(R.mipmap.img_load_empty, "I am Title $it", "I am desc I am desc I am desc I am descI am desc $it", "2011-12-25 20:00") }
    }

    private fun getFooterView(): List<View>? {
        val footers = ArrayList<View>()
        val footer1 = layoutInflater.inflate(R.layout.layout_footer_home, refreshRecyclerView.parent as ViewGroup, false)
        footers.add(footer1)
        return footers
    }

    var tvCountdownTime : TextView? = null
    private var mMarqueeVerticalTextView : MarqueeVerticalTextView? = null
    private fun getHeaderView(): List<View> {
        val headers = ArrayList<View>()

        val header1 = layoutInflater.inflate(R.layout.layout_header_home_banner, refreshRecyclerView.parent as ViewGroup, false)
        val mBanner = header1.findViewById(R.id.mBanner) as BGABanner
        mBanner.setData(null, ImageView.ScaleType.CENTER_CROP,R.mipmap.img_banner_1,R.mipmap.img_banner_2,R.mipmap.img_banner_3,R.mipmap.img_banner_4)
//        mBanner.setData(getBannerImages(),getBannerTips())
        mBanner.setDelegate { banner, itemView, model, position ->
            showToast("点击了第" + (position + 1) + "张")
        }
        headers.add(header1)

        val header2 = layoutInflater.inflate(R.layout.layout_header_home_sort, refreshRecyclerView.parent as ViewGroup, false)
        //初始化数据源
        val mGridViewPager = header2.findViewById(R.id.mGridViewPager) as GridViewPager
        val mFlycoPageIndicaor = header2.findViewById(R.id.indicator_circle_snap) as FlycoPageIndicaor
        mGridViewPager
                .setGridItemClickListener { pos, position, str ->
                    showToast(pos.toString() + "/" + str)
                }
                //传入String的List 必须作为最后一步
                .init(initData())
        mFlycoPageIndicaor
                .setIsSnap(true)
                .setViewPager(mGridViewPager.viewPager, mGridViewPager.pageCount)
        headers.add(header2)

        val header3 = layoutInflater.inflate(R.layout.layout_header_home_huodong, refreshRecyclerView.parent as ViewGroup, false)
        val mMarqueeVerticalTextView = header3.findViewById(R.id.marquee_v_tv) as MarqueeVerticalTextView
         tvCountdownTime = header3.findViewById(R.id.tv_countdown_time) as TextView
        val tvHuoDong1 = header3.findViewById(R.id.tv_huodong_1) as TextView
        val tvHuoDong2 = header3.findViewById(R.id.tv_huodong_2) as TextView
        tvCountdownTime?.setOnClickListener {
            showToast(tvCountdownTime?.text as String)
        }
        tvHuoDong1?.setOnClickListener {
            showToast(tvHuoDong1?.text as String)
        }
        tvHuoDong2?.setOnClickListener {
            showToast(tvHuoDong2?.text as String)
        }
        //跑马灯广告
        mMarqueeVerticalTextView.setTextList(getMarpueeData())
        mMarqueeVerticalTextView.setText(14f, 5, -0x899eaa)//设置属性
        mMarqueeVerticalTextView.setTextStillTime(3000)//设置停留时长间隔
        mMarqueeVerticalTextView.setAnimTime(500)//设置进入和退出的时间间隔
        mMarqueeVerticalTextView.setOnItemClickListener {
            showToast("点击了 : " + lists[it])
        }
        mMarqueeVerticalTextView.startAutoScroll()
        startRun()
        headers.add(header3)
        return headers
    }


    private fun getBannerImages(): ArrayList<Int>? {
        val bannerImages = ArrayList<Int>()
        bannerImages.add(R.mipmap.img_banner_1)
        bannerImages.add(R.mipmap.img_banner_2)
        bannerImages.add(R.mipmap.img_banner_3)
        bannerImages.add(R.mipmap.img_banner_4)
        return bannerImages
    }

    private fun getBannerTips(): List<String>? {
        val bannerTips = ArrayList<String>()
        bannerTips.add("新一代战斗机")
        bannerTips.add("海市蜃楼")
        bannerTips.add("极地考察")
        bannerTips.add("绿地草原")
        return bannerTips
    }

    /**
     * 初始化数据源
     */
    private val titles = arrayOf("美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "甜品饮品",
            "汽车服务", "景点", "足疗按摩", "运动健身", "今日新单", "小吃快餐", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装")

    private fun initData(): List<SortModel> {
        val mData = ArrayList<SortModel>()
        for (i in titles.indices) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            val imageId = resources.getIdentifier("icon_logo", "mipmap", VFrame.getContext().packageName)
            mData.add(SortModel(titles[i], imageId))
        }
        return mData
    }


    internal var lists = ArrayList<String>()
    private fun getMarpueeData(): ArrayList<String> {
        lists.clear()
        for (i in 0..4) {
            val s = "I am Marpuee " + i
            lists.add(s)
        }
        return lists
    }

    // 倒计时 天 ,小时,分钟,秒
    private var mDay: Long = 10
    private var mHour: Long = 10
    private var mMin: Long = 30
    private var mSecond: Long = 23
    private val isRun = true
    private val timeHandler = object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                computeTime()
                if (mDay == 0L && mHour == 0L && mMin == 0L && mSecond == 0L) {
                    tvCountdownTime?.text = "活动已结束"
                } else {
                    tvCountdownTime?.text = activity?.getString(R.string.text_countdown_time, mDay, mHour, mMin, mSecond)
                }
            }
        }
    }
    /**
     * 开启倒计时
     */
    private fun startRun() {
        Thread(Runnable {
            // TODO Auto-generated method stub
            while (isRun) {
                try {
                    Thread.sleep(1000) // sleep 1000ms
                    val message = Message.obtain()
                    message.what = 1
                    timeHandler.sendMessage(message)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }).start()
    }


    /**
     * 倒计时计算
     */
    private fun computeTime() {
        mSecond--
        if (mSecond < 0) {
            mMin--
            mSecond = 59
            if (mMin < 0) {
                mMin = 59
                mHour--
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23
                    mDay--
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mMarqueeVerticalTextView != null) {
            mMarqueeVerticalTextView?.startAutoScroll()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mMarqueeVerticalTextView != null) {
            mMarqueeVerticalTextView?.stopAutoScroll()
        }
    }

}