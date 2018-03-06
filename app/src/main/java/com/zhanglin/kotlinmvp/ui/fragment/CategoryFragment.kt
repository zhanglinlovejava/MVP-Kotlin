package com.zhanglin.kotlinmvp.ui.fragment

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseFragment
import com.zhanglin.kotlinmvp.mvp.contract.CategoryContract
import com.zhanglin.kotlinmvp.mvp.model.bean.CategoryEntity
import com.zhanglin.kotlinmvp.mvp.presenter.CategoryPresenter
import com.zhanglin.kotlinmvp.ui.activity.CategaryDetailActivity
import com.zhanglin.kotlinmvp.ui.adapter.CategoryAdapter
import com.zhanglin.kotlinmvp.utils.DisplayManager
import kotlinx.android.synthetic.main.frag_category.*

/**
 * Created by zhanglin on 2018/3/1.
 */
class CategoryFragment : BaseFragment(), CategoryContract.View, BaseAdapter.OnRecyclerViewItemClickListener {
    private var mAdapter: CategoryAdapter? = null
    private val mPresenter by lazy {
        CategoryPresenter()
    }

    companion object {
        fun getInstance(): CategoryFragment {
            val cf = CategoryFragment()
            return cf
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun getLayoutId(): Int = R.layout.frag_category

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusViewCategory
        mAdapter = CategoryAdapter(activity)
        mCategoryRecyclerView.adapter = mAdapter
        mCategoryRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        mAdapter?.setOnRecyclerViewItemClickListener(this)
        mCategoryRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent?.getChildAdapterPosition(view)
                val offset = DisplayManager.dip2px(3f)
                var left = if (position!! % 2 != 0) offset else 0
                outRect?.set(left!!, offset!!, 0, 0)
            }
        })
    }

    override fun onItemClick(view: View?, position: Int) {
        CategaryDetailActivity.actionLaunch(activity, mAdapter?.getItem(position)!!)
    }

    override fun lazyLoad() {
        mPresenter.getCategoryData()
    }

    override fun setCategoryData(list: ArrayList<CategoryEntity>) {
        mAdapter?.setNewData(list)
    }

    override fun showError() {
        mLayoutStatusView?.showError()
    }
}