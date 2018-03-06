package com.zhanglin.kotlinmvp.mvp.model

import com.zhanglin.kotlinmvp.api.RetrofitManager
import com.zhanglin.kotlinmvp.mvp.model.bean.CategoryEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhanglin on 2018/3/1.
 */
class CategoryModel {
    fun getCategoryData(): Observable<ArrayList<CategoryEntity>> {
        return RetrofitManager.service.getCategoryData().subscribeOn(Schedulers.io())
    }
}