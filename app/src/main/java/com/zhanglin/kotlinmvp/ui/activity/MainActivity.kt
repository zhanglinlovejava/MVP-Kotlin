package com.zhanglin.kotlinmvp.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseActivity
import com.zhanglin.kotlinmvp.mvp.model.bean.TabEntity
import com.zhanglin.kotlinmvp.ui.fragment.DiscoverFragment
import com.zhanglin.kotlinmvp.ui.fragment.HomeFragment
import com.zhanglin.kotlinmvp.ui.fragment.HotFragment
import com.zhanglin.kotlinmvp.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mTitles = arrayOf("每日精选", "发现", "热门", "我的")
    private val mIconNomral = intArrayOf(R.mipmap.ic_home_normal, R.mipmap.ic_discovery_normal, R.mipmap.ic_hot_normal, R.mipmap.ic_mine_normal)
    private val mIconSelected = intArrayOf(R.mipmap.ic_home_selected, R.mipmap.ic_discovery_selected, R.mipmap.ic_hot_selected, R.mipmap.ic_mine_selected)
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private var mIndex = 0
    private var mMineFragment: MineFragment? = null
    private var mHomeFragment: HomeFragment? = null
    private var mDiscoverFragment: DiscoverFragment? = null
    private var mHotFragment: HotFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
    }

    private fun initTab() {
        (0 until mTitles.size)
                .mapTo(mTabEntities) {
                    TabEntity(mTitles[it], mIconSelected[it], mIconNomral[it])
                }
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView() {
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }

    private fun switchFragment(position: Int) {
        var transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0
            -> mHomeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance().let {
                mHomeFragment = it
                transaction.add(R.id.fl_container, it, "home")
            }
            1
            -> mDiscoverFragment?.let {
                transaction.show(it)
            } ?: DiscoverFragment.getInstance(mTitles[position]).let {
                mDiscoverFragment = it
                transaction.add(R.id.fl_container, it, "discover")
            }
            2
            -> mHotFragment?.let {
                transaction.show(it)
            } ?: HotFragment.getInstance().let {
                mHotFragment = it
                transaction.add(R.id.fl_container, it, "hot")
            }
            3
            -> mMineFragment?.let {
                transaction.show(it)
            } ?: MineFragment.getInstance().let {
                mMineFragment = it
                transaction.add(R.id.fl_container, it, "mine")
            }
        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let {
            transaction.hide(mHomeFragment)
        }
        mDiscoverFragment?.let {
            transaction.hide(mDiscoverFragment)
        }
        mHotFragment?.let {
            transaction.hide(mHotFragment)
        }
        mMineFragment?.let {
            transaction.hide(mMineFragment)
        }

    }

    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun showLoading() {
        //todo
    }

    override fun hideLoading() {
        //todo
    }
}
