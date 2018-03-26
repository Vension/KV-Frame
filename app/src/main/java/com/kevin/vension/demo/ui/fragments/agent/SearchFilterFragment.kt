package com.kevin.vension.demo.ui.fragments.agent

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.flexbox.FlexboxLayout
import com.kevin.vension.demo.R
import com.kevin.vension.demo.base.BaseFragment
import com.kevin.vension.demo.models.TestEntity
import com.vension.frame.VFrame
import com.vension.frame.utils.VSizeUtil
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_search_filter.*

/**
 * @author ：Created by vension on 2018/3/9.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class SearchFilterFragment : BaseFragment(){

    private var tags = arrayOf("篮球", "足球", "排球", "唱K", "Android", "iOS", "PHP", "Kotlin for Java", "程序员鼓励师", "生活家", "屌丝程序员", "想法", "短篇小说", "美食", "教育", "奇思妙想", "飞鸽传情", "NBA直播")
    private val tagInfos = ArrayList<TestEntity>()

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_search_filter
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.centerTextView.text = "搜索"
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        val searchKey = mBundle?.getString("search_key")
        if (!searchKey.isNullOrEmpty()){
            tags[0] = searchKey.toString()
        }
        setTagData()
    }

    private fun setTagData() {
        for (i in tags.indices) {
            val model = TestEntity(i,tags[i])
            tagInfos.add(model)
            mFlexboxLayout.addView(createNewFlexItemTextView(model, i))
        }
    }

    override fun lazyLoadData() {
    }


    /**
     * 动态创建TextView
     * @param tagInfo
     * @return
     */
    private fun createNewFlexItemTextView(tagInfo: TestEntity, index: Int): TextView {
        val textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.text = tagInfo.title
        textView.textSize = 12.0f
        if (index == 0) {
            tagInfo.isSelected = true
            tvTags.text = tagInfo.title
        }
        textView.setTextColor(ContextCompat.getColor(VFrame.getContext(), if (tagInfo.isSelected) R.color.white else R.color.colorAccent))
        textView.setBackgroundResource(if (tagInfo.isSelected) R.drawable.shape_rect_select else R.drawable.shape_rect_unselect)
        textView.tag = tagInfo.id
        textView.setOnClickListener {
            tagInfo.isSelected = !tagInfo.isSelected
            Toast.makeText(context, tagInfo.title, Toast.LENGTH_LONG).show()
            textView.setTextColor(ContextCompat.getColor(VFrame.getContext(), if (tagInfo.isSelected()) R.color.white else R.color.colorAccent))
            textView.setBackgroundResource(if (tagInfo.isSelected) R.drawable.shape_rect_select else R.drawable.shape_rect_unselect)
            showTags()
        }
        val padding = VSizeUtil.dpTopx(context, 4)
        val paddingLeftAndRight = VSizeUtil.dpTopx(context, 8)
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding)
        val layoutParams = FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        val margin = VSizeUtil.dpTopx(context, 6)
        val marginTop = VSizeUtil.dpTopx(context, 16)
        layoutParams.setMargins(margin, marginTop, margin, 0)
        textView.layoutParams = layoutParams
        return textView
    }

    /**
     * 显示选中的tag
     */
    private fun showTags() {
        if (tagInfos.size > 0) {
            var mStringBuffer = StringBuffer("")
            for (i in tagInfos.indices) {
                if (tagInfos[i].isSelected) {
                    mStringBuffer.append(tagInfos[i].title + "   ")
                }
            }
            tvTags.text = mStringBuffer.toString()
        }
    }

}