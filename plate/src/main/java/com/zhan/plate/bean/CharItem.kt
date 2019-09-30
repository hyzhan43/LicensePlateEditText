package com.zhan.plate.bean

import android.widget.TextView

/**
 *  @author:  hyzhan
 *  @date:    2019/7/22
 *  @desc:    TODO
 */
data class CharItem(
    val current: TextView,
    val pre: TextView? = null,
    val next: TextView? = null
)