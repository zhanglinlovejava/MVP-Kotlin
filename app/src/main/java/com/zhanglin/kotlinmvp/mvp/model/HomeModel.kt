package com.zhanglin.kotlinmvp.mvp.model

import com.zhanglin.kotlinmvp.api.RetrofitManager
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhanglin on 2018/2/26.
 */
class HomeModel {
    fun requestHomeData(): Observable<HomeEntity> {
        return RetrofitManager.service.getFirstHomeData(1).subscribeOn(Schedulers.io())
    }

    fun loadMoreHomeData(url: String): Observable<HomeEntity> {
        return RetrofitManager.service.getMoreHomeData(url).subscribeOn(Schedulers.io())
    }
}