package com.zhanglin.kotlinmvp.mvp.presenter

import com.zhanglin.kotlinmvp.base.BasePresenter
import com.zhanglin.kotlinmvp.mvp.contract.HotContract
import com.zhanglin.kotlinmvp.mvp.model.HotModel
import com.zhanglin.kotlinmvp.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by zhanglin on 2018/3/2.
 */
class HotPresenter : BasePresenter<HotContract.View>(), HotContract.Presenter {
    private val hotModel by lazy {
        HotModel()
    }

    override fun getRandTabData() {
        mRootView?.apply {
            showLoading()
            compositeDisposable.add(hotModel.getRankTabData().observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ issue ->
                        setRandTabData(issue.tabInfo)
                        hideLoading()
                    }, { error ->
                        showError()
                        Logger.e(error.message.toString())
                    }))
        }
    }
}