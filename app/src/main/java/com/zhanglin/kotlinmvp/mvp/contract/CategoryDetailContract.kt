package com.zhanglin.kotlinmvp.mvp.contract

import com.zhanglin.kotlinmvp.base.IBaseRecyclerView
import com.zhanglin.kotlinmvp.base.IPresenter
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity

/**
 * Created by zhanglin on 2018/3/1.
 */
interface CategoryDetailContract {
    interface View : IBaseRecyclerView {
        fun setCategoryDetail(list: ArrayList<HomeEntity.Issue.Item>)
        fun setMoreIssue(list: ArrayList<HomeEntity.Issue.Item>)
    }

    interface Presenter : IPresenter<View> {
        fun getCategoryDetail(id: Long)

        fun getMoreIssue()
    }
}