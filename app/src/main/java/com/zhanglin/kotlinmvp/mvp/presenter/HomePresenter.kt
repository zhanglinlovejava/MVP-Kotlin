package com.zhanglin.kotlinmvp.mvp.presenter

import com.zhanglin.kotlinmvp.base.BasePresenter
import com.zhanglin.kotlinmvp.mvp.contract.HomeContract
import com.zhanglin.kotlinmvp.mvp.model.HomeModel
import com.zhanglin.kotlinmvp.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by zhanglin on 2018/2/26.
 */

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    private val homeModel by lazy {
        HomeModel()
    }
    private var nextPageUrl: String? = null
    override fun requestHomeData(isRefresh: Boolean) {
        checkViewAttached()
        mRootView?.apply {
            if (!isRefresh)
                showLoading()
            compositeDisposable.add(homeModel.requestHomeData().observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ homeBean ->
                        onRefreshCompleted()
                        showRecycleContent()
                        nextPageUrl = homeBean.nextPageUrl
                        homeBean.issueList[0].itemList.filter { item ->
                            item.type != "video"
                        }.forEach({ item ->
                            homeBean.issueList[0].itemList.remove(item)
                        })
                        setHomeData(homeBean)
                    }, { error ->
                        onRefreshCompleted()
                        if (!isRefresh)
                            showNetErrView()
                        Logger.e("请求失败了 " + error.toString())
                    }))
        }
    }

    override fun loadMoreData() {
        var disposed = nextPageUrl?.let {
            homeModel.loadMoreHomeData(nextPageUrl!!).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ homeBean ->
                        mRootView?.apply {
                            nextPageUrl = homeBean.nextPageUrl
                            homeBean.issueList[0].itemList.filter { item ->
                                item.type != "video"
                            }.forEach({ item ->
                                homeBean.issueList[0].itemList.remove(item)
                            })
                            setMoreData(homeBean)
                        }
                    }, { error ->
                        mRootView?.apply {
                            Logger.e("请求失败了 " + error.toString())
                        }

                    })
        }
        if (disposed != null) {
            compositeDisposable.add(disposed)
        }
    }
}