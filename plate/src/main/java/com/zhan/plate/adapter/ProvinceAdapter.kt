package com.zhan.plate.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhan.plate.R

/**
 *  @author:  hyzhan
 *  @date:    2019/7/22
 *  @desc:    TODO
 */
class ProvinceAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_plate_item, null) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.let {
            it.addOnClickListener(R.id.mTvWord)
            it.setText(R.id.mTvWord, item ?: "")
        }
    }
}