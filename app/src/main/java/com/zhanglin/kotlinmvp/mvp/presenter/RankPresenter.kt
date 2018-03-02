package com.zhanglin.kotlinmvp.mvp.presenter

import com.zhanglin.kotlinmvp.base.BasePresenter
import com.zhanglin.kotlinmvp.mvp.contract.RankContract
import com.zhanglin.kotlinmvp.mvp.model.RankModel
import com.zhanglin.kotlinmvp.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by zhanglin on 2018/3/2.
 */
class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {
    private val rankModel by lazy {
        RankModel()
    }

    override fun getRankList(url: String, isRefresh: Boolean) {
        url.apply {
            mRootView?.apply {
                if (!isRefresh)
                    showLoading()
                compositeDisposable.add(rankModel.getRankList(url).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ issue ->

                            hideLoading()
                            onRefreshCompleted()
                            setRankListData(issue.itemList)
                        }, { error ->
                            showNetErrView()
                            onRefreshCompleted()
                            Logger.e(error.message.toString())
                        }))
            }
        }
    }
}