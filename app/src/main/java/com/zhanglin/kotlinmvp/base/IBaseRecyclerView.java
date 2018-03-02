package com.zhanglin.kotlinmvp.base;

/**
 * Created by zhanglin on 2018/1/17.
 */

public interface IBaseRecyclerView extends IBaseView {
    /**
     * 显示没有数据的View
     */
    void showEmptyView();

    /**
     * 显示网络错误的View
     */
    void showNetErrView();

    /**
     * 显示RecycleView里面内容，表示成功并且有数据
     */
    void showRecycleContent();

    /**
     * 刷新完成，隐藏加载进度对话框
     */
    void onRefreshCompleted();

    void onLoadMoreComplete();

    /**
     * 没有更多的数据,禁用掉上拉刷新
     */
    void noMoreData();
}
