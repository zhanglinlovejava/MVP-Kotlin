package com.zhanglin.kotlinmvp.mvp.presenter

import com.shuyu.gsyvideoplayer.utils.NetworkUtils
import com.zhanglin.kotlinmvp.KotlinApplication
import com.zhanglin.kotlinmvp.base.BasePresenter
import com.zhanglin.kotlinmvp.mvp.contract.VideoContract
import com.zhanglin.kotlinmvp.mvp.model.VideoModel
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import com.zhanglin.kotlinmvp.utils.Logger
import com.zhanglin.kotlinmvp.utils.dataFormat
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by zhanglin on 2018/2/27.
 */
class VideoPresenter : BasePresenter<VideoContract.View>(), VideoContract.Presenter {
    private val videoModel by lazy {
        VideoModel()
    }

    override fun parsePlayData(itemData: HomeEntity.Issue.Item) {
        mRootView?.apply {
            val playInfo = itemData.data?.playInfo
            val is4G = NetworkUtils.is4G(KotlinApplication.context)
            val type = if (!is4G) "high" else "normal"
            if (playInfo!!.size > 1) {
                playInfo.filter {
                    it.type == type
                }.forEach {
                    val url = it.url
                    startPlay(url)
                    if (is4G) {
                        showToast("本次消耗 ${dataFormat(it.urlList[0].size)}流量")
                    }
                }
            } else {
                mRootView?.startPlay(itemData.data.playUrl)
            }
            updateVideoInfo(itemData)
        }

    }

    override fun getVideoDetailData(id: Long) {
        checkViewAttached()
        mRootView?.apply {
            showLoading()
            compositeDisposable.add(videoModel.getVideoDetailData(id).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ issue ->
                        hideLoading()
                        setVideoDetailData(issue.itemList)
                    }, { error ->
                        Logger.e("请求出错了" + error.message.toString())
                    }))
        }
    }
}