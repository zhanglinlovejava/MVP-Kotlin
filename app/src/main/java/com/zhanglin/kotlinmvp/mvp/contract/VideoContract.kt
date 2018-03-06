package com.zhanglin.kotlinmvp.mvp.contract

import com.zhanglin.kotlinmvp.base.IBaseRecyclerView
import com.zhanglin.kotlinmvp.base.IPresenter
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity

/**
 * Created by zhanglin on 2018/2/27.
 */
interface VideoContract {
    interface View : IBaseRecyclerView {
        fun startPlay(url: String)
        fun setVideoDetailData(itemList: ArrayList<HomeEntity.Issue.Item>)
        fun updateVideoInfo(itemInfo: HomeEntity.Issue.Item)
    }

    interface Presenter : IPresenter<View> {
        fun parsePlayData(itemInfo: HomeEntity.Issue.Item)

        fun getVideoDetailData(id: Long)
    }
}