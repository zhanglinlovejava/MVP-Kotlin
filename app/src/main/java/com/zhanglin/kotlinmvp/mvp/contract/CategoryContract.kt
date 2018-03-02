package com.zhanglin.kotlinmvp.mvp.contract

import com.zhanglin.kotlinmvp.base.IBaseView
import com.zhanglin.kotlinmvp.base.IPresenter
import com.zhanglin.kotlinmvp.mvp.model.bean.CategoryBean

/**
 * Created by zhanglin on 2018/3/1.
 */
interface CategoryContract {
    interface View : IBaseView {
        fun setCategoryData(list: ArrayList<CategoryBean>)
        fun showError()
    }

    interface Presenter : IPresenter<View> {
        fun getCategoryData()
    }
}