package com.zhanglin.kotlinmvp.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseRecyclerFragment
import com.zhanglin.kotlinmvp.mvp.contract.HomeContract
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean
import com.zhanglin.kotlinmvp.mvp.presenter.HomePresenter
import com.zhanglin.kotlinmvp.ui.adapter.HomeAdapter
import com.zhanglin.kotlinmvp.utils.actionVideoLaunch
import kotlinx.android.synthetic.main.frag_home.*

/**
 * Created by zhanglin on 2018/2/26.
 */

class HomeFragment : BaseRecyclerFragment(), HomeContract.View, OnRefreshLoadmoreListener, BaseAdapter.OnRecyclerViewItemClickListener {

    private var mAdapter: HomeAdapter? = null
    private val mPresenter by lazy {
        HomePresenter()
    }

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_home
    }

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        mRefreshLayout = refreshLayout
        refreshLayout.setPadding(0, 0, 0, 0)
        refreshLayout.setOnRefreshLoadmoreListener(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter = HomeAdapter(activity)
        recyclerView.adapter = mAdapter
        mAdapter?.setOnRecyclerViewItemClickListener(this)
    }

    override fun lazyLoad() {
        mPresenter.requestHomeData(1, false)
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun setHomeData(homeData: HomeBean) {
        mLayoutStatusView?.showContent()
        mAdapter?.setNewData(homeData.issueList[0].itemList)
    }

    override fun setMoreData(homeData: HomeBean) {
        mAdapter?.addData(homeData.issueList[0].itemList)
    }


    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        mPresenter.loadMoreData()
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        mPresenter.requestHomeData(1, true)
    }

    override fun onItemClick(view: View?, position: Int) {
        actionVideoLaunch(activity, mAdapter?.getItem(position)!!, view!!)
    }

}