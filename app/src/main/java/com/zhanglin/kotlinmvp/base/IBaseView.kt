package com.zhanglin.kotlinmvp.base

/**
 * Created by zhanglin on 2018/2/26.
 */

interface IBaseView {
    fun showLoading()
    fun hideLoading()
    fun showToast(msg: String)
}
