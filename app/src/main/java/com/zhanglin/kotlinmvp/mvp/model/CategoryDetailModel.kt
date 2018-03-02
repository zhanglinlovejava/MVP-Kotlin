package com.zhanglin.kotlinmvp.mvp.model

import com.zhanglin.kotlinmvp.api.RetrofitManager
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhanglin on 2018/3/1.
 */
class CategoryDetailModel {
    fun getCategoryDetail(id: Long): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getCategoryDetail(id).subscribeOn(Schedulers.io())
    }

    fun getMoreIssue(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getMoreIssue(url).subscribeOn(Schedulers.io())
    }
}