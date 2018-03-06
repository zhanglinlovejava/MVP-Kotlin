package com.zhanglin.kotlinmvp.ui.fragment

import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseRecyclerFragment
import com.zhanglin.kotlinmvp.mvp.contract.HomeContract
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeEntity
import com.zhanglin.kotlinmvp.mvp.presenter.HomePresenter
import com.zhanglin.kotlinmvp.ui.activity.SearchActivity
import com.zhanglin.kotlinmvp.ui.adapter.HomeAdapter
import com.zhanglin.kotlinmvp.utils.StatusBarUtil
import com.zhanglin.kotlinmvp.utils.actionVideoLaunch
import kotlinx.android.synthetic.main.frag_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zhanglin on 2018/2/26.
 */

class HomeFragment : BaseRecyclerFragment(), HomeContract.View, BaseAdapter.OnRecyclerViewItemClickListener {

    private var mAdapter: HomeAdapter? = null
    private var loadingMore = false
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
        StatusBarUtil.setPaddingSmart(activity, rel_toolbar)
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        recyclerView.layoutManager = linearLayoutManager
        mAdapter = HomeAdapter(activity)
        recyclerView.adapter = mAdapter
        mAdapter?.setOnRecyclerViewItemClickListener(this)
        iv_search.setOnClickListener({
            openSearchActivity()
        })
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = recyclerView?.childCount
                    val itemCount = recyclerView?.layoutManager?.itemCount
                    val firstVisibleItem = (recyclerView?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount!! == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    rel_toolbar.setBackgroundColor(resources.getColor(R.color.translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mAdapter?.data?.size!! > 1) {
                        rel_toolbar.setBackgroundColor(resources.getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        tv_header_title.text = simpleDateFormat.format(mAdapter?.data!![currentVisibleItemPosition].data?.date)
                    }
                }
            }
        })
    }
    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, iv_search, iv_search.transitionName)
            startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }
    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    override fun lazyLoad() {
        mPresenter.requestHomeData(false)
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun setHomeData(homeData: HomeEntity) {
        mLayoutStatusView?.showContent()
        mAdapter?.setNewData(homeData.issueList[0].itemList)
    }

    override fun setMoreData(homeData: HomeEntity) {
        loadingMore = false
        mAdapter?.addData(homeData.issueList[0].itemList)
    }

    override fun onItemClick(view: View?, position: Int) {
        actionVideoLaunch(activity, mAdapter?.getItem(position)!!, view!!)
    }

}