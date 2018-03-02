package com.zhanglin.kotlinmvp.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by zhanglin on 2018/2/26.
 */

object RetrofitManager {

    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    val service: ApiService by lazy {
        getRetrofit()!!.create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitManager::class.java) {
                if (retrofit == null) {
                    client = OkHttpClient.Builder()
                            .addInterceptor(addHeaderInterceptor())
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .build()

                    retrofit = Retrofit.Builder()
                            .baseUrl("http://baobab.kaiyanapp.com/api/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        return retrofit
    }

    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    .header("token", "")
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }
}
