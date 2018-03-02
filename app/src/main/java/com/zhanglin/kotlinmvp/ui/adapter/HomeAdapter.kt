package com.zhanglin.kotlinmvp.ui.adapter

import android.content.Context
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseViewHolder
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean
import com.zhanglin.kotlinmvp.utils.durationFormat
import java.util.*

/**
 * Created by zhanglin on 2018/2/27.
 */
class HomeAdapter : BaseAdapter<HomeBean.Issue.Item> {

    constructor(context: Context?) : super(context, R.layout.item_home_content, ArrayList())

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {
        helper?.setImageUrl(R.id.iv_avatar, item?.data?.author?.icon)
        helper?.setText(R.id.tv_title, item?.data?.title)
        helper?.setImageUrl(R.id.iv_cover_feed, item?.data?.cover?.feed)
        helper?.setText(R.id.tv_category, "#" + item?.data?.category)
        var tagText = "#"
        item?.data?.tags?.forEach {
            tagText += it.name + "/"
        }
        tagText += durationFormat(item?.data?.duration)
        helper?.setText(R.id.tv_tag, tagText)

    }
}