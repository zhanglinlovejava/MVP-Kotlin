package com.zhanglin.kotlinmvp.mvp.model

import com.zhanglin.kotlinmvp.api.RetrofitManager
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhanglin on 2018/2/26.
 */
class HomeModel {
    fun requestHomeData(num: Int): Observable<HomeBean> {
        return RetrofitManager.service.getFirstHomeData(1).subscribeOn(Schedulers.io())
    }

    fun loadMoreHomeData(url: String): Observable<HomeBean> {
        return RetrofitManager.service.getMoreHomeData(url).subscribeOn(Schedulers.io())
    }
}