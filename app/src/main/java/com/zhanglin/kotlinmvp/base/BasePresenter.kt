package com.zhanglin.kotlinmvp.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by zhanglin on 2018/2/26.
 */

open class BasePresenter<T : IBaseView> : IPresenter<T> {

    var mRootView: T? = null
        private set
    var compositeDisposable = CompositeDisposable()
    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    private val isViewAttached: Boolean
        get() = mRootView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before requesting data to the IPresenter")
}
