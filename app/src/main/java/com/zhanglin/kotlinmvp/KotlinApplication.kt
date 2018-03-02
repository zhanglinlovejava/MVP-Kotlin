package com.zhanglin.kotlinmvp

import android.app.Application
import android.content.Context
import com.zhanglin.kotlinmvp.utils.DisplayManager
import kotlin.properties.Delegates

/**
 * Created by zhanglin on 2018/2/28.
 */
class KotlinApplication : Application() {

    companion object {
        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        DisplayManager.init(this)

    }
}