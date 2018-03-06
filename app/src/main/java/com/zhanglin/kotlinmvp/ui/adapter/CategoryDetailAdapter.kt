package com.zhanglin.kotlinmvp.ui.adapter

import android.content.Context
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseViewHolder
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import com.zhanglin.kotlinmvp.utils.durationFormat

/**
 * Created by zhanglin on 2018/3/1.
 */
class CategoryDetailAdapter(context: Context) : BaseAdapter<HomeEntity.Issue.Item>(context, R.layout.item_category_detail, ArrayList<HomeEntity.Issue.Item>()) {

    override fun convert(helper: BaseViewHolder?, item: HomeEntity.Issue.Item?) {
        with(helper!!){
            setText(R.id.tv_tag,"#${item?.data?.category}/${durationFormat(item?.data?.duration)}")
            setImageUrl(R.id.iv_image,item?.data?.cover?.feed)
        }
    }
}