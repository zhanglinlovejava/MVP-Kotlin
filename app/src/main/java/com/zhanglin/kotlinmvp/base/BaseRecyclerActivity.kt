package com.zhanglin.kotlinmvp.base

import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
 * Created by zhanglin on 2018/2/27.
 */

abstract class BaseRecyclerActivity : BaseActivity(), IBaseRecyclerView {
    protected var mRefreshLayout: RefreshLayout? = null

    override fun showEmptyView() {
        mLayoutStatusView?.showEmpty()
    }

    override fun showNetErrView() {
        mLayoutStatusView?.showError()
    }

    override fun showRecycleContent() {
        mLayoutStatusView?.showContent()
    }

    override fun onRefreshCompleted() {
        mRefreshLayout?.finishRefresh()
        mRefreshLayout?.setEnableLoadmore(true)
    }

    override fun onLoadMoreComplete() {
        mRefreshLayout?.finishLoadmore()
    }

    override fun noMoreData() {
        mRefreshLayout?.finishLoadmore()
        mRefreshLayout?.setEnableLoadmore(false)
    }
}