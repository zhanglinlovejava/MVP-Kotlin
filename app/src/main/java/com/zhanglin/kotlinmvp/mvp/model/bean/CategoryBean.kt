package com.zhanglin.kotlinmvp.mvp.model.bean

import java.io.Serializable

/**
 * Created by zhanglin on 2018/3/1.
 */
data class CategoryBean(val id: Long, val name: String, val description: String, val bgPicture: String, val bgColor: String, val headerImage: String) : Serializable