package com.zhanglin.kotlinmvp.mvp.presenter

import com.zhanglin.kotlinmvp.base.BasePresenter
import com.zhanglin.kotlinmvp.mvp.contract.CategoryContract
import com.zhanglin.kotlinmvp.mvp.model.CategoryModel
import com.zhanglin.kotlinmvp.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by zhanglin on 2018/3/1.
 */
class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {
    private val categoryModel by lazy {
        CategoryModel()
    }

    override fun getCategoryData() {
        mRootView?.apply {
            showLoading()
            compositeDisposable.add(categoryModel.getCategoryData().observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ issue ->
                        hideLoading()
                        setCategoryData(issue)
                    }, { error ->
                        hideLoading()
                        showError()
                        Logger.e(error.message.toString())
                    }))
        }

    }
}