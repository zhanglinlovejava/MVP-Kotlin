package com.zhanglin.kotlinmvp.ui.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseViewHolder
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity

/**
 * Created by zhanglin on 2018/3/1.
 */
class FollowAdapter(context: Context?) : BaseAdapter<HomeEntity.Issue.Item>(context, R.layout.item_follow, ArrayList()) {

    override fun convert(helper: BaseViewHolder?, item: HomeEntity.Issue.Item?) {
        with(helper!!){
            var header = item?.data?.header
            setText(R.id.tv_title,header?.title)
            setImageUrl(R.id.iv_avatar,header?.icon)
            setText(R.id.tv_desc,header?.description)
            val recyclerView = getView<RecyclerView>(R.id.fl_recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = FollowHorizontalAdapter(mContext, item?.data?.itemList!!)
        }
    }
}