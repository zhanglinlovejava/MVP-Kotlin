package com.zhanglin.kotlinmvp.mvp.contract

import com.zhanglin.kotlinmvp.base.IBaseView
import com.zhanglin.kotlinmvp.base.IPresenter
import com.zhanglin.kotlinmvp.mvp.model.bean.RankTabBean

/**
 * Created by zhanglin on 2018/3/2.
 */
interface HotContract {
    interface View : IBaseView {
        fun setRandTabData(tabInfo:RankTabBean.TabInfo)
        fun showError()
    }

    interface Presenter : IPresenter<View> {
        fun getRandTabData()
    }
}