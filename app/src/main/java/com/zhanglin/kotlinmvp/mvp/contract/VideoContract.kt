package com.zhanglin.kotlinmvp.mvp.contract

import com.zhanglin.kotlinmvp.base.IBaseRecyclerView
import com.zhanglin.kotlinmvp.base.IPresenter
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhanglin on 2018/2/27.
 */
interface VideoContract {
    interface View : IBaseRecyclerView {
        fun startPlay(url: String)
        fun setVideoDetailData(itemList: ArrayList<HomeBean.Issue.Item>)
        fun updateVideoInfo(itemInfo: HomeBean.Issue.Item)
    }

    interface Presenter : IPresenter<View> {
        fun parsePlayData(itemInfo: HomeBean.Issue.Item)

        fun getVideoDetailData(id: Long)
    }
}