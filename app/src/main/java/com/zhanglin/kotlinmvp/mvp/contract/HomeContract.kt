package com.zhanglin.kotlinmvp.mvp.contract

import com.zhanglin.kotlinmvp.base.IBaseRecyclerView
import com.zhanglin.kotlinmvp.base.IPresenter
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhanglin on 2018/2/26.
 */

interface HomeContract {
    interface View : IBaseRecyclerView {
        fun setHomeData(homeData: HomeBean)

        fun setMoreData(homeData: HomeBean)

    }

    interface Presenter : IPresenter<View> {

        fun requestHomeData(num: Int,isRefresh:Boolean)

        fun loadMoreData()
    }
}
