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
                PlateItem.CHAR -> {
                    setText(R.id.mTvWord, item?.data ?: "")
                    addOnClickListener(R.id.mTvWord)
                }
                PlateItem.EMPTY -> {
                    itemView.mCvContainer.gone()
                }
                PlateItem.DELETE -> {
                    addOnClickListener(R.id.mIvDelete)
                }
                else -> {
                }
            }
        }
    }
}