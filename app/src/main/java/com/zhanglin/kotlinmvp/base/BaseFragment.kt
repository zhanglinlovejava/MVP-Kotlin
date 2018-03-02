package com.zhanglin.kotlinmvp.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.classic.common.MultipleStatusView

/**
 * Created by zhanglin on 2018/2/26.
 */

abstract class BaseFragment : Fragment() ,IBaseView{
    //视图是否加载完毕
    private var isViewPrepare = false
    private var hasLoadData = false
    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayoutId(), null)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    override fun showToast(msg: String) {
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
    }
    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * 初始化 ViewI
     */
    abstract fun initView()

    /**
     * 懒加载
     */
    abstract fun lazyLoad()
}
