package com.zhanglin.kotlinmvp.ui.adapter

import android.app.Activity
import android.content.Context
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseViewHolder
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import com.zhanglin.kotlinmvp.utils.actionVideoLaunch
import com.zhanglin.kotlinmvp.utils.durationFormat

/**
 * Created by zhanglin on 2018/3/1.
 */
class FollowHorizontalAdapter(context: Context, itemList: ArrayList<HomeEntity.Issue.Item>) : BaseAdapter<HomeEntity.Issue.Item>(context, R.layout.item_follow_horizontal, itemList) {

    override fun convert(helper: BaseViewHolder?, item: HomeEntity.Issue.Item?) {
        with(helper!!) {
            setImageUrl(R.id.iv_cover_feed, item?.data?.cover?.feed)
            setText(R.id.tv_title, item?.data?.title)
            if (item?.data?.tags != null && item.data.tags.size > 0) {
                setText(R.id.tv_tag, "#${item.data.tags[0].name} / ${durationFormat(item.data.duration)}")
            } else {
                setText(R.id.tv_tag, "#${durationFormat(item?.data?.duration)}")
            }
            setOnClickListener(R.id.iv_cover_feed, {
                actionVideoLaunch(mContext as Activity, item!!, getView(R.id.iv_cover_feed))
            })

        }
    }
}