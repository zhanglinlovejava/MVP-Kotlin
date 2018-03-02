package com.zhanglin.kotlinmvp.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseRecyclerFragment
import com.zhanglin.kotlinmvp.mvp.contract.FollowContract
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean
import com.zhanglin.kotlinmvp.mvp.presenter.FollowPresenter
import com.zhanglin.kotlinmvp.ui.adapter.FollowAdapter
import kotlinx.android.synthetic.main.frag_follow.*

/**
 * Created by zhanglin on 2018/3/1.
 */
class FollowFragment : BaseRecyclerFragment(), FollowContract.View, OnLoadmoreListener {

    private var mAdapter: FollowAdapter? = null

    companion object {
        fun getInstance(): FollowFragment {
            val fragment = FollowFragment()
            return fragment

        }
    }

    private val mPresenter by lazy {
        FollowPresenter()
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_follow
    }

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusViewFollow
        mRefreshLayout = refreshLayoutFollow
        refreshLayoutFollow.isEnableRefresh = false
        refreshLayoutFollow.setPadding(0, 0, 0, 0)
        refreshLayoutFollow.setOnLoadmoreListener(this)
        mAdapter = FollowAdapter(activity)
        mFollowRecyclerView.adapter = mAdapter
        mFollowRecyclerView.layoutManager = LinearLayoutManager(activity)

    }

    override fun lazyLoad() {
        mPresenter.getFollowData()
    }

    override fun setFollowData(list: ArrayList<HomeBean.Issue.Item>) {
        mAdapter?.setNewData(list)
    }

    override fun onDestroyView() {
        mPresenter.detachView()
        super.onDestroyView()
    }

    override fun setMoreIssue(list: ArrayList<HomeBean.Issue.Item>) {
        mAdapter?.addData(list)
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        mPresenter.getMoreIssue()
    }
}