package com.zhanglin.kotlinmvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseRecyclerFragment
import com.zhanglin.kotlinmvp.mvp.contract.RankContract
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean
import com.zhanglin.kotlinmvp.mvp.presenter.RankPresenter
import com.zhanglin.kotlinmvp.ui.adapter.CategoryDetailAdapter
import com.zhanglin.kotlinmvp.utils.actionVideoLaunch
import kotlinx.android.synthetic.main.frag_rank.*

/**
 * Created by zhanglin on 2018/3/2.
 */
class RankFragment : BaseRecyclerFragment(), RankContract.View, BaseAdapter.OnRecyclerViewItemClickListener, OnRefreshListener {
    private var mAdapter: CategoryDetailAdapter? = null
    private var url: String? = null

    private val mPresenter by lazy {
        RankPresenter()
    }

    companion object {
        fun getInstance(url: String): RankFragment {
            var rf = RankFragment()
            var bundle = Bundle()
            bundle.putString("url", url)
            rf.arguments = bundle
            return rf
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
        mLayoutStatusView.let {

        }
    }


    override fun getLayoutId(): Int = R.layout.frag_rank

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusViewRank
        mRefreshLayout = refreshLayoutRank
        refreshLayoutRank.isEnableLoadmore = false
        refreshLayoutRank.setOnRefreshListener(this)
        url = arguments.getString("url")
        mAdapter = CategoryDetailAdapter(activity)
        mRankRecyclerView.adapter = mAdapter
        mRankRecyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter?.setOnRecyclerViewItemClickListener(this)
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        mPresenter.getRankList(url!!, true)
    }

    override fun lazyLoad() {
        mPresenter.getRankList(url!!, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun setRankListData(list: ArrayList<HomeBean.Issue.Item>) {
        mAdapter?.setNewData(list)
    }

    override fun onItemClick(view: View?, position: Int) {
        actionVideoLaunch(activity, mAdapter?.getItem(position)!!, view!!)
    }
}