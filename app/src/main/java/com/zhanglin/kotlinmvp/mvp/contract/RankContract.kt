package com.zhanglin.kotlinmvp.mvp.contract

import com.zhanglin.kotlinmvp.base.IBaseRecyclerView
import com.zhanglin.kotlinmvp.base.IPresenter
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhanglin on 2018/3/2.
 */
interface RankContract {
    interface View : IBaseRecyclerView {
        fun setRankListData(list: ArrayList<HomeBean.Issue.Item>)
    }

    interface Presenter : IPresenter<View> {
        fun getRankList(url:String,isRefresh:Boolean)
    }
}