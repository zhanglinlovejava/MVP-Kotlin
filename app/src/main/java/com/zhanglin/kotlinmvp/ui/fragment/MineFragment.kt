package com.zhanglin.kotlinmvp.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.TextPaint
import android.view.View
import com.opensource.svgaplayer.*
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseFragment
import kotlinx.android.synthetic.main.frag_mine.*
import java.net.URL

/**
 * Created by zhanglin on 2018/2/26.
 */

class MineFragment : BaseFragment(), SVGACallback, SVGAParser.ParseCompletion, View.OnClickListener {
    var svgParse: SVGAParser? = null
    var isFirst = true
    companion object {
        fun getInstance(): MineFragment {
            val fragment = MineFragment()
            return fragment

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_mine
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        svgParse = SVGAParser(activity)
        mLayoutStatusView = multipleStatusViewMine
        ivPlaySVG.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        showLoading()
        svgParse?.parse(URL("https://github.com/yyued/SVGA-Samples/blob/master/kingset.svga?raw=true"), this)
    }

    override fun onComplete(videoItem: SVGAVideoEntity) {
        hideLoading()
        ivPlaySVG.visibility = View.GONE
        var sVGADrawable = SVGADrawable(videoItem, requestDynamicItem())
        svgaImageView.setImageDrawable(sVGADrawable)
        svgaImageView.loops = 1
        svgaImageView.callback = this
        svgaImageView.startAnimation()
    }


    /**
     * 进行简单的文本替换
     * @return
     */
    private fun requestDynamicItem(): SVGADynamicEntity {
        val dynamicEntity = SVGADynamicEntity()
        val textPaint = TextPaint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 28f
        dynamicEntity.setDynamicText("Pony 送了一打风油精给主播", textPaint, "banner")
        return dynamicEntity
    }

    override fun onError() {
        showToast("播放失败")
        hideLoading()
        ivPlaySVG.visibility = View.VISIBLE
    }

    override fun onFinished() {
        if (isFirst) {
            isFirst = false
            svgParse?.parse("sample.svga", this)
        } else {
            svgaImageView.clearAnimation()
            ivPlaySVG.visibility = View.VISIBLE
        }
    }

    override fun onRepeat() {

    }

    override fun onStep(frame: Int, percentage: Double) {
        //todo
    }

    override fun lazyLoad() {

    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        svgaImageView.clearAnimation()
    }
}
