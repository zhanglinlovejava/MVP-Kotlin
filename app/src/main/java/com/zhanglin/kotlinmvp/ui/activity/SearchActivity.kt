package com.zhanglin.kotlinmvp.ui.activity

import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseActivity
import com.zhanglin.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.act_search.*

/**
 * Created by zhanglin on 2018/3/6.
 */
class SearchActivity : BaseActivity() {



    override fun showLoading() {
        //todo
    }

    override fun hideLoading() {
        //todo
    }

    override fun layoutId(): Int = R.layout.act_search

    override fun initView() {
        StatusBarUtil.setPaddingSmart(mContext,tvSearch)
    }
}