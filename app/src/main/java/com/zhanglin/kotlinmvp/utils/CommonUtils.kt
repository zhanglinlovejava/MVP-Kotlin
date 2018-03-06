@file:Suppress("UNREACHABLE_CODE")

package com.zhanglin.kotlinmvp.utils

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import com.zhanglin.kotlinmvp.ui.activity.VideoPlayActivity

/**
 * Created by zhanglin on 2018/2/27.
 */

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

/**
 * 数据流量格式化
 */
fun dataFormat(total: Long): String {
    var result: String
    var speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}

fun actionVideoLaunch(activity: Activity, item: HomeEntity.Issue.Item, view: View) {
    val intent = Intent(activity, VideoPlayActivity::class.java)
    intent.putExtra("itemData", item)
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        val pair = Pair<View, String>(view, VideoPlayActivity.IMG_TRANSITION)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, pair)
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
    } else {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }
}
