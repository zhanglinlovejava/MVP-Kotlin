package com.zhanglin.kotlinmvp.mvp.contract

import com.zhanglin.kotlinmvp.base.IBaseRecyclerView
import com.zhanglin.kotlinmvp.base.IPresenter
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity

/**
 * Created by zhanglin on 2018/2/26.
 */

interface HomeContract {
    interface View : IBaseRecyclerView {
        fun setHomeData(homeData: HomeEntity)

        fun setMoreData(homeData: HomeEntity)

    }

    interface Presenter : IPresenter<View> {

        fun requestHomeData(isRefresh: Boolean)

        fun loadMoreData()
    }
}
