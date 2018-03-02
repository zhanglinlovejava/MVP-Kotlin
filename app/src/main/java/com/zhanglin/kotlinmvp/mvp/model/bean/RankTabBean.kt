package com.zhanglin.kotlinmvp.mvp.model.bean

/**
 * Created by zhanglin on 2018/3/2.
 */
data class RankTabBean(var tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)
    data class Tab(var id: Int, var name: String, var apiUrl: String)
}