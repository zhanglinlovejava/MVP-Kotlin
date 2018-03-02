package com.zhanglin.kotlinmvp.mvp.presenter

import com.zhanglin.kotlinmvp.base.BasePresenter
import com.zhanglin.kotlinmvp.mvp.contract.CategoryDetailContract
import com.zhanglin.kotlinmvp.mvp.model.CategoryDetailModel
import com.zhanglin.kotlinmvp.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by zhanglin on 2018/3/1.
 */
class CategoryDetailPresenter : BasePresenter<CategoryDetailContract.View>(), CategoryDetailContract.Presenter {
    private val categoryDetailModel by lazy {
        CategoryDetailModel()
    }
    private var nextPageUrl: String? = null
    override fun getCategoryDetail(id: Long) {
        mRootView?.apply {
            compositeDisposable.add(categoryDetailModel.getCategoryDetail(id).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ issue ->
                        hideLoading()
                        setCategoryDetail(issue.itemList)
                        nextPageUrl = issue.nextPageUrl
                    }, { error ->
                        showNetErrView()
                        Logger.e(error.message.toString())

                    }))
        }

    }

    override fun getMoreIssue() {
        var hasMore = nextPageUrl?.apply {
            mRootView?.apply {
                compositeDisposable.add(categoryDetailModel.getMoreIssue(nextPageUrl!!).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ issue ->
                            nextPageUrl = issue.nextPageUrl
                            onLoadMoreComplete()
                            setMoreIssue(issue.itemList)
                        }, { error ->
                            showNetErrView()
                            Logger.e(error.message.toString())

                        }))
            }
        }
        if (hasMore == null) {
            mRootView?.noMoreData()
        }
    }
}