package com.zhanglin.kotlinmvp.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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

    /**
     *
     * 打开软键盘
     */
    fun openKeyBord(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    override fun showToast(msg: String) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show()
    }
}
