package com.zhanglin.kotlinmvp.mvp.model

import com.zhanglin.kotlinmvp.api.RetrofitManager
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhanglin on 2018/3/2.
 */
class RankModel {
    fun getRankList(url: String): Observable<HomeEntity.Issue> {
        return RetrofitManager.service.getMoreIssue(url).subscribeOn(Schedulers.io())
    }
}