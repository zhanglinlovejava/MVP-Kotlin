package com.zhanglin.kotlinmvp.base

/**
 * Created by zhanglin on 2018/2/26.
 */

interface IPresenter<in V : IBaseView> {
    fun attachView(mRootView: V)

    fun detachView()
}