package com.zhanglin.kotlinmvp.mvp.contract

import com.zhanglin.kotlinmvp.base.IBaseRecyclerView
import com.zhanglin.kotlinmvp.base.IPresenter
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity

/**
 * Created by zhanglin on 2018/3/2.
 */
interface RankContract {
    interface View : IBaseRecyclerView {
        fun setRankListData(list: ArrayList<HomeEntity.Issue.Item>)
    }

    interface Presenter : IPresenter<View> {
        fun getRankList(url:String,isRefresh:Boolean)
    }
}