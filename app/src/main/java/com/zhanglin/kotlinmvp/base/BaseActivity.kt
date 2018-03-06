package com.zhanglin.kotlinmvp.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.classic.common.MultipleStatusView
import com.zhanglin.kotlinmvp.utils.StatusBarUtil

/**
 * Created by zhanglin on 2018/2/26.
 */

abstract class BaseActivity : AppCompatActivity() ,IBaseView {

    protected var mLayoutStatusView: MultipleStatusView? = null
    open lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.darkMode(this)
        mContext = this
        setContentView(layoutId())
        initView()
        initListener()
    }

    abstract fun layoutId(): Int

    abstract fun initView()


    private fun initListener() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
    }

    override fun showToast(msg: String) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show()
    }
}
