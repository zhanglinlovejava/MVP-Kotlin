package com.zhanglin.kotlinmvp.mvp.presenter

import com.zhanglin.kotlinmvp.base.BasePresenter
import com.zhanglin.kotlinmvp.mvp.contract.FollowContract
import com.zhanglin.kotlinmvp.mvp.model.FollowModel
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by zhanglin on 2018/3/1.
 */
class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presenter {
    private var nextPageUrl: String? = null
    private val followModel by lazy {
        FollowModel()
    }

    override fun getFollowData() {
        mRootView?.showLoading()
        compositeDisposable.add(followModel.getFollowData().observeOn(AndroidSchedulers.mainThread())
                .subscribe({ issue ->
                    mRootView?.apply {
                        hideLoading()
                        setFollowData(issue.itemList)
                        nextPageUrl = issue.nextPageUrl
                    }
                }, { error ->
                    mRootView?.showNetErrView()
                }))
    }

    override fun getMoreIssue() {
        var hasMore = nextPageUrl?.let {
            mRootView?.apply {
                compositeDisposable.add(followModel.getMoreIssue(nextPageUrl!!).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ issue ->
                            onLoadMoreComplete()
                            setMoreIssue(issue.itemList)
                            nextPageUrl = issue.nextPageUrl
                        }, { error ->
                            showNetErrView()
                            showToast(error.message.toString())
                        }))
            }
        }
        if (hasMore == null) {
            mRootView?.noMoreData()
        }

    }
}