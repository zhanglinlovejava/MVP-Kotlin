package com.zhanglin.kotlinmvp.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.zhanglin.kotlinmvp.R
import com.zhanglin.kotlinmvp.base.BaseAdapter
import com.zhanglin.kotlinmvp.base.BaseViewHolder
import com.zhanglin.kotlinmvp.mvp.model.bean.HomeBean
import com.zhanglin.kotlinmvp.utils.durationFormat

/**
 * Created by zhanglin on 2018/2/28.
 */
class VideoAdapter : BaseAdapter<HomeBean.Issue.Item> {

    companion object {
        val TYPE_DETAIL = 0
        val TYPE_TEXT_CARD = 1
        val TYPE_SMALL_CARD = 2
    }

    constructor(context: Context?) : super(context)


    override fun getItemViewType(position: Int): Int {
        when {
            position == 0 ->
                return TYPE_DETAIL
            getItem(position).type == "textCard" ->
                return TYPE_TEXT_CARD
            getItem(position).type == "videoSmallCard" ->
                return TYPE_SMALL_CARD
        }
        throw IllegalAccessException("Api 解析出错了，出现其他类型")
    }

    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        var view: View
        when {
            viewType == TYPE_DETAIL -> {
                view = View.inflate(parent?.context, R.layout.item_video_detail_info, null)
                return VideoDetailHolder(parent?.context, view)
            }
            viewType == TYPE_TEXT_CARD -> {
                view = View.inflate(parent?.context, R.layout.item_video_text_card, null)
                return VideoTextCardHolder(parent?.context, view)
            }
            viewType == TYPE_SMALL_CARD -> {
                view = View.inflate(parent?.context, R.layout.item_video_small_card, null)
                return VideoSmallCardHolder(parent?.context, view)
            }
        }
        return super.onCreateDefViewHolder(parent, viewType)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {
        with(helper!!) {
            when (itemViewType) {
                TYPE_DETAIL -> {
                    item?.data?.title?.let { setText(R.id.tv_title, it) }
                    item?.data?.description?.let { setText(R.id.expandable_text, it) }
                    setText(R.id.tv_tag, "#${item?.data?.category} / ${durationFormat(item?.data?.duration)}")
                    setText(R.id.tv_action_favorites, item?.data?.consumption?.collectionCount.toString())
                    setText(R.id.tv_action_share, item?.data?.consumption?.shareCount.toString())
                    setText(R.id.tv_action_reply, item?.data?.consumption?.replyCount.toString())
                    if (item?.data?.author != null) {
                        setText(R.id.tv_author_name, item.data.author.name)
                        setText(R.id.tv_author_desc, item.data.author.description)
                        setImageUrl(R.id.iv_avatar, item.data.author.icon)
                    } else {
                        setVisible(R.id.layout_author_view, false)
                    }
                }
                TYPE_TEXT_CARD -> {
                     setText(R.id.tv_text_card, item?.data?.text)
                }
                TYPE_SMALL_CARD -> {
                    setText(R.id.tv_title_small, item?.data?.title)
                    setText(R.id.tv_tag, "#${item?.data?.category} / ${durationFormat(item?.data?.duration)}")
                    setImageUrl(R.id.iv_video_small_card, item?.data?.cover?.detail)
                }
                else -> {
                }
            }
        }
    }

    class VideoDetailHolder : BaseViewHolder {
        constructor(context: Context?, view: View?) : super(context, view)
    }

    class VideoTextCardHolder : BaseViewHolder {
        constructor(context: Context?, view: View?) : super(context, view)
    }

    class VideoSmallCardHolder : BaseViewHolder {
        constructor(context: Context?, view: View?) : super(context, view)
    }
}