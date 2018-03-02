package com.zhanglin.kotlinmvp.ui.fragment

import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseFragment

/**
 * Created by zhanglin on 2018/2/26.
 */

class MineFragment : BaseFragment() {
    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            fragment.mTitle = title
            return fragment

        }
    }
    override fun getLayoutId(): Int {
        return R.layout.frag_mine
    }

    override fun initView() {

    }

    override fun lazyLoad() {

    }

    override fun showLoading() {
        //todo
    }

    override fun hideLoading() {
        //todo
    }
}
