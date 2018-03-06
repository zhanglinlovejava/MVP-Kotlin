package com.zhanglin.kotlinmvp.ui.adapter

import android.content.Context
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseViewHolder
import com.zhanglin.kotlinmvp.mvp.model.bean.CategoryEntity

/**
 * Created by zhanglin on 2018/3/1.
 */
class CategoryAdapter(context: Context) : BaseAdapter<CategoryEntity>(context, R.layout.item_category, ArrayList<CategoryEntity>()) {
    override fun convert(helper: BaseViewHolder?, item: CategoryEntity?) {
        with(helper!!) {
            setImageUrl(R.id.iv_category, item?.bgPicture)
            setText(R.id.tv_category_name, "#${item?.name}")
        }
    }
}