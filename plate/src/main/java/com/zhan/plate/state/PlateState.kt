package com.zhan.plate.state

import android.widget.TextView
import com.zhan.plate.PlateView

/**
 *  @author:  hyzhan
 *  @date:    2019/7/23
 *  @desc:    TODO
 */
interface PlateState {

    /**
     *  显示最后一个输入框
     */
    fun displayPlateBox(textView: TextView)

    /**
     *  是否是最后一个 TextView
     */
    fun isLastTextView(current: Int): Boolean

    /**
     *  获取车牌
     */
    fun getPlate(plateList: List<TextView>): String
}