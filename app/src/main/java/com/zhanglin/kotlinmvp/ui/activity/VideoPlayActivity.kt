package com.zhanglin.kotlinmvp.ui.activity

import android.annotation.TargetApi
import android.content.res.Configuration
import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.`interface`.VideoListener
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseRecyclerActivity
import com.zhanglin.kotlinmvp.mvp.contract.VideoContract
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import com.zhanglin.kotlinmvp.mvp.presenter.VideoPresenter
import com.zhanglin.kotlinmvp.ui.adapter.VideoAdapter
import com.zhanglin.kotlinmvp.utils.DisplayManager
import com.zhanglin.kotlinmvp.utils.Logger
import kotlinx.android.synthetic.main.act_video.*

/**
 * Created by zhanglin on 2018/2/27.
 */
class VideoPlayActivity : BaseRecyclerActivity(), VideoContract.View, BaseAdapter.OnRecyclerViewItemClickListener {

    companion object {
        val IMG_TRANSITION = "IMG_TRANSITION"
    }

    private var itemData: HomeEntity.Issue.Item? = null
    private var orientationUtils: OrientationUtils? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    private var transition: Transition? = null
    private var mAdapter: VideoAdapter? = null
    private val mPresenter by lazy {
        VideoPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.act_video
    }

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusViewVideo
        itemData = intent.getSerializableExtra("itemData") as HomeEntity.Issue.Item?
        initTransition()
        initVideoViewConfig()
        mAdapter = VideoAdapter(mContext)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        Glide.with(mContext)
                .load("http://img.kaiyanapp.com/f31b4170c1b1a9c295b1cd74f3b56b72.jpeg?imageMogr2/quality/60/format/jpg/thumbnail/" +
                        "${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(200f)!!}x${DisplayManager.getScreenWidth()}")
                .into(mVideoBackground)
        mAdapter?.setOnRecyclerViewItemClickListener(this)

    }

    override fun startPlay(url: String) {
        mVideoView.setUp(url, false, "")
        mVideoView.startPlayLogic()
    }

    private fun initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            mPresenter.parsePlayData(itemData!!)
        }
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

            override fun onTransitionEnd(p0: Transition?) {
                Logger.d("onTransitionEnd()------")
                mPresenter.parsePlayData(itemData!!)
                transition?.removeListener(this)
            }

        })
    }

    override fun updateVideoInfo(itemInfo: HomeEntity.Issue.Item) {
        itemData = itemInfo
        mAdapter?.clearData()
        mAdapter?.add(0, itemInfo!!)
        mPresenter.getVideoDetailData(itemData?.data?.id!!)
    }

    override fun onItemClick(view: View?, position: Int) {
        if (mAdapter?.getItemViewType(position) == VideoAdapter.Companion.TYPE_SMALL_CARD) {
            mPresenter.parsePlayData(mAdapter?.getItem(position)!!)
        }
    }

    private fun initVideoViewConfig() {
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)
        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).load(itemData?.data?.cover?.feed).into(imageView)
        mVideoView.thumbImageView = imageView
        mVideoView.setStandardVideoAllCallBack(object : VideoListener {
            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true

            }

            override fun onPlayError(url: String, vararg objects: Any) {
                super.onPlayError(url, *objects)
                showToast("播放失败")
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                Logger.e("***** onQuitFullscreen **** ")
                //列表返回的样式判断
                orientationUtils?.backToProtVideo()

            }
        })
        //设置返回键的功能
        mVideoView.backButton.setOnClickListener({
            onBackPressed()
        })
        //设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener({
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoView.startWindowFullscreen(mContext, true, true)
        })
        //锁屏事件
        mVideoView.setLockClickListener(object : LockClickListener {
            override fun onClick(view: View?, lock: Boolean) {
                //配合下方的onConfigurationChanged
                orientationUtils?.isEnable = !lock
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }

    /**
     * 监听返回键
     */
    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this))
            return
        //释放所有
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) run {
            super.onBackPressed()
        } else {
            finish()
            overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
        }
    }

    override fun setVideoDetailData(itemList: ArrayList<HomeEntity.Issue.Item>) {
        mAdapter?.addData(itemList)
        recyclerView.scrollToPosition(0)
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun showNetErrView() {
        mLayoutStatusView?.showError()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
        mPresenter.detachView()
    }
}