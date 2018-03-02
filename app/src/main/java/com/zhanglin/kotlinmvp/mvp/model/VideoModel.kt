package com.zhanglin.kotlinmvp.mvp.model

import com.zhanglin.kotlinmvp.api.RetrofitManager
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhanglin on 2018/2/27.
 */
class VideoModel {
    fun getVideoDetailData(id: Long): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getRelatedData(id).subscribeOn(Schedulers.io())
    }
}