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
        mRootView?.apply {
            showLoading()
            compositeDisposable.add(followModel.getFollowData().observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ issue ->
                        hideLoading()
                        setFollowData(issue.itemList)
                        nextPageUrl = issue.nextPageUrl
                    }, { error ->
                        showNetErrView()
                    }))
        }
    }

    override fun getMoreIssue() {
        mRootView?.apply {
            var hasMore = nextPageUrl?.let {
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
            if (hasMore == null) {
                mRootView?.noMoreData()
            }
        }

    }
}