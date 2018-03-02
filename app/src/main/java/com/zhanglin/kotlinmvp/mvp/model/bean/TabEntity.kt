package com.zhanglin.kotlinmvp.mvp.model.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Created by zhanglin on 2018/2/26.
 */
class TabEntity(var title: String, private var slectedIcon: Int, private var unSelectedIcon: Int) : CustomTabEntity {
    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return slectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}