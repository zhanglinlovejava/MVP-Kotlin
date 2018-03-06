package com.zhanglin.kotlinmvp.ui.fragment

import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseFragment
import com.zhanglin.kotlinmvp.mvp.contract.HotContract
import com.zhanglin.kotlinmvp.mvp.model.bean.RankTabEntity
import com.zhanglin.kotlinmvp.mvp.presenter.HotPresenter
import com.zhanglin.kotlinmvp.ui.adapter.MainAdapter
import com.zhanglin.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.frag_discover.*

/**
 * Created by zhanglin on 2018/2/26.
 */

class HotFragment : BaseFragment(), HotContract.View {
    private val tabList = ArrayList<String>()
    private val fragments = ArrayList<BaseFragment>()
    private val mPresenter by lazy {
        HotPresenter()
    }

    companion object {
        fun getInstance( ): HotFragment {
            val fragment = HotFragment()
            return fragment

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_discover
    }

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusViewHot
        StatusBarUtil.setPaddingSmart(activity, discoverToolbar)
    }

    override fun onDestroyView() {
        mPresenter.detachView()
        super.onDestroyView()
    }

    override fun lazyLoad() {
        mPresenter.getRandTabData()
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun setRandTabData(tabInfo: RankTabEntity.TabInfo) {
        for (tab in tabInfo.tabList) {
            tabList.add(tab.name)
            fragments.add(RankFragment.getInstance(tab.apiUrl))
        }
        mViewPager.adapter = MainAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)

    }

    override fun showError() {
        mLayoutStatusView?.showError()
    }
}