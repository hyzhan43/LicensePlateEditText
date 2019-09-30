package com.zhan.plate.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *  @author:  hyzhan
 *  @date:    2019/7/22
 *  @desc:    TODO
 */
class PlateItem(private val itemType: Int, val data: String = "") : MultiItemEntity {

    companion object {
        // 数字 (1、2、3、4)
        const val CHAR = 0
        // 空, 用来调整位置
        const val EMPTY = 1
        const val DELETE = 2
    }

    override fun getItemType(): Int = itemType
}