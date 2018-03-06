package com.zhanglin.kotlinmvp.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseRecyclerActivity
import com.zhanglin.kotlinmvp.mvp.contract.CategoryDetailContract
import com.zhanglin.kotlinmvp.mvp.model.bean.CategoryEntity
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import com.zhanglin.kotlinmvp.mvp.presenter.CategoryDetailPresenter
import com.zhanglin.kotlinmvp.ui.adapter.CategoryDetailAdapter
import com.zhanglin.kotlinmvp.utils.StatusBarUtil
import com.zhanglin.kotlinmvp.utils.actionVideoLaunch
import kotlinx.android.synthetic.main.act_category_detail.*

/**
 * Created by zhanglin on 2018/3/1.
 */
class CategaryDetailActivity : BaseRecyclerActivity(), CategoryDetailContract.View, BaseAdapter.OnRecyclerViewItemClickListener {

    private var mAdapter: CategoryDetailAdapter? = null
    private var categoryData: CategoryEntity? = null
    private var loadingMore = false
    private val mPresenter by lazy {
        CategoryDetailPresenter()
    }

    companion object {
        fun actionLaunch(context: Context, categoryData: CategoryEntity) {
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
        StatusBarUtil.setPaddingSmart(mContext, toolbar)
        categoryData = intent.getSerializableExtra("categoryData") as CategoryEntity?
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
        refreshLayout.isEnableLoadmore = false
        mAdapter = CategoryDetailAdapter(mContext)
        mCategoryDetailRecyclerView.adapter = mAdapter
        mCategoryDetailRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter?.setOnRecyclerViewItemClickListener(this)
        mPresenter.getCategoryDetail(categoryData?.id!!)
        Glide.with(mContext).load(categoryData?.headerImage).into(imageView)
        mCategoryDetailRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = recyclerView?.childCount
                    val itemCount = recyclerView?.layoutManager?.itemCount
                    val firstVisibleItem = (recyclerView?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount!! == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.getMoreIssue()
                        }
                    }
                }
            }

        })
    }

    override fun setCategoryDetail(list: ArrayList<HomeEntity.Issue.Item>) {
        mAdapter?.setNewData(list)
    }

    override fun setMoreIssue(list: ArrayList<HomeEntity.Issue.Item>) {
        loadingMore = false
        mAdapter?.addData(list)
    }

    override fun onItemClick(view: View?, position: Int) {
        actionVideoLaunch(this, mAdapter?.getItem(position)!!, view!!)
    }
}