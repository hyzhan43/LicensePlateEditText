package com.zhan.plate.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhan.plate.bean.PlateItem
import com.zhan.ktwing.ext.gone
import com.zhan.plate.R
import kotlinx.android.synthetic.main.layout_plate_item.view.*

/**
 *  @author:  hyzhan
 *  @date:    2019/7/22
 *  @desc:    TODO
 */
class CharAdapter : BaseMultiItemQuickAdapter<PlateItem, BaseViewHolder>(null) {

    init {
        addItemType(PlateItem.CHAR, R.layout.layout_plate_item)
        addItemType(PlateItem.EMPTY, R.layout.layout_plate_item)
        addItemType(PlateItem.DELETE, R.layout.layout_plate_delete)
    }


    override fun convert(helper: BaseViewHolder?, item: PlateItem?) {

        helper?.run {
            when (itemViewType) {
                PlateItem.CHAR -> convertChar(item)
                PlateItem.EMPTY -> convertEmpty()
                PlateItem.DELETE -> convertDelete()
                else -> {
                }
            }
        }
    }

    private fun BaseViewHolder.convertDelete() {
        addOnClickListener(R.id.mIvDelete)
    }

    private fun BaseViewHolder.convertEmpty() {
        itemView.mCvContainer.gone()
    }

    private fun BaseViewHolder.convertChar(item: PlateItem?) {
        setText(R.id.mTvWord, item?.data ?: "")
        addOnClickListener(R.id.mTvWord)
    }
}