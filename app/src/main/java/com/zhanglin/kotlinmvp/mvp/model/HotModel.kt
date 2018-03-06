package com.zhanglin.kotlinmvp.mvp.model

import com.zhanglin.kotlinmvp.api.RetrofitManager
import com.zhanglin.kotlinmvp.mvp.model.bean.RankTabEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhanglin on 2018/3/2.
 */
class HotModel {
    fun getRankTabData(): Observable<RankTabEntity> {
        return RetrofitManager.service.getRankTabList().subscribeOn(Schedulers.io())
    }
}