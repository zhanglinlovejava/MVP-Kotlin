package com.zhanglin.kotlinmvp.api

import com.zhanglin.kotlinmvp.mvp.model.bean.CategoryEntity
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import com.zhanglin.kotlinmvp.mvp.model.bean.RankTabEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by zhanglin on 2018/2/27.
 */
interface ApiService {
    /**
     * 首页精选
     *
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeEntity>

    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeEntity>

    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<HomeEntity.Issue>

    @GET("v4/tabs/follow")
    fun getFollowData(): Observable<HomeEntity.Issue>

    @GET
    fun getMoreIssue(@Url url: String): Observable<HomeEntity.Issue>

    @GET("v4/categories")
    fun getCategoryData(): Observable<ArrayList<CategoryEntity>>

    @GET("v4/categories/videoList")
    fun getCategoryDetail(@Query("id") id: Long): Observable<HomeEntity.Issue>

    @GET("v4/rankList")
    fun getRankTabList(): Observable<RankTabEntity>
}