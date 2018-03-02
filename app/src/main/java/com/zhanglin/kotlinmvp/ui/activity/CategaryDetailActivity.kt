package com.zhanglin.kotlinmvp.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseRecyclerActivity
import com.zhanglin.kotlinmvp.mvp.contract.CategoryDetailContract
import com.zhanglin.kotlinmvp.mvp.model.bean.CategoryBean
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean
import com.zhanglin.kotlinmvp.mvp.presenter.CategoryDetailPresenter
import com.zhanglin.kotlinmvp.ui.adapter.CategoryDetailAdapter
import com.zhanglin.kotlinmvp.utils.StatusBarUtil
import com.zhanglin.kotlinmvp.utils.actionVideoLaunch
import kotlinx.android.synthetic.main.act_category_detail.*

/**
 * Created by zhanglin on 2018/3/1.
 */
class CategaryDetailActivity : BaseRecyclerActivity(), CategoryDetailContract.View, BaseAdapter.OnRecyclerViewItemClickListener, OnLoadmoreListener {

    private var mAdapter: CategoryDetailAdapter? = null
    private var categoryData: CategoryBean? = null
    private val mPresenter by lazy {
        CategoryDetailPresenter()
    }

    companion object {
        fun actionLaunch(context: Context, categoryData: CategoryBean) {
            context.startActivity(Intent(context, CategaryDetailActivity::class.java)
                    .putExtra("categoryData", categoryData))
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun layoutId(): Int = R.layout.act_category_detail


    @SuppressLint("SetTextI18n")
    override fun initView() {
        mPresenter.attachView(this)
        StatusBarUtil.setPaddingSmart(mContext,toolbar)
        categoryData = intent.getSerializableExtra("categoryData") as CategoryBean?
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        collapsing_toolbar_layout.title = categoryData?.name
        tv_category_desc.text = "#${categoryData?.description}#"
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)
        mLayoutStatusView = multipleStatusViewCategoryDetail
        mRefreshLayout = refreshLayout
        refreshLayout.isEnableRefresh = false
        refreshLayout.setOnLoadmoreListener(this)
        mAdapter = CategoryDetailAdapter(mContext)
        mCategoryDetailRecyclerView.adapter = mAdapter
        mCategoryDetailRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter?.setOnRecyclerViewItemClickListener(this)
        mPresenter.getCategoryDetail(categoryData?.id!!)
        Glide.with(mContext).load(categoryData?.headerImage).into(imageView)
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        mPresenter.getMoreIssue()
    }

    override fun setCategoryDetail(list: ArrayList<HomeBean.Issue.Item>) {
        mAdapter?.setNewData(list)
    }

    override fun setMoreIssue(list: ArrayList<HomeBean.Issue.Item>) {
        mAdapter?.addData(list)
    }

    override fun onItemClick(view: View?, position: Int) {
        actionVideoLaunch(this, mAdapter?.getItem(position)!!, view!!)
    }
}