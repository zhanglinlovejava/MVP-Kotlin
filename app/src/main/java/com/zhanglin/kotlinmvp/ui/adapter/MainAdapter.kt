package com.zhanglin.kotlinmvp.ui.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.zhanglin.kotlinmvp.base.BaseFragment

/**
 * Created by zhanglin on 2018/02/28.
 */
class MainAdapter(private val fragmentManager: FragmentManager, private val fragments: List<BaseFragment> // 每个Fragment对应一个Page
                  ,private val  mTitles: ArrayList<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return fragments.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(fragments[position].view) // 移出viewpager两边之外的page布局
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any? {
        val fragment = fragments[position]
        if (!fragment.isAdded) { // 如果fragment还没有added
            val ft = fragmentManager.beginTransaction()
            ft.add(fragment, fragment.javaClass.simpleName)
            ft.commitAllowingStateLoss()
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。
             * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            fragmentManager.executePendingTransactions()
        }

        if (fragment.view!!.parent == null) {
            container.addView(fragment.view) // 为viewpager增加布局
        }

        return fragment.view
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}
