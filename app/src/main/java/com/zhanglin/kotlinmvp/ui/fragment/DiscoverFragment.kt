package com.zhanglin.kotlinmvp.ui.fragment

import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseFragment
import com.zhanglin.kotlinmvp.ui.adapter.MainAdapter
import com.zhanglin.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.frag_discover.*

/**
 * Created by zhanglin on 2018/2/26.
 */

class DiscoverFragment : BaseFragment() {
    private val tabList = ArrayList<String>()
    private var mTitle: String? = null
    private val fragments = ArrayList<BaseFragment>()

    companion object {
        fun getInstance(title: String): DiscoverFragment {
            val fragment = DiscoverFragment()
            fragment.mTitle = title
            return fragment

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_discover
    }

    override fun initView() {
        StatusBarUtil.setPaddingSmart(activity, discoverToolbar)
        tv_header_title.text = mTitle
        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance())
        fragments.add(CategoryFragment.getInstance())
        mViewPager.adapter = MainAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)

    }

    override fun lazyLoad() {

    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

}