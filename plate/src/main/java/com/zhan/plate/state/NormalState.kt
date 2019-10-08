package com.zhan.plate.state

import android.widget.TextView
import com.zhan.plate.PlateView
import com.zhan.ktwing.ext.gone
import com.zhan.plate.R
import kotlinx.android.synthetic.main.layout_plate_view.view.*

/**
 *  @author:  hyzhan
 *  @date:    2019/7/23
 *  @desc:    普通车牌
 */
class NormalState : PlateState {

    override fun displayPlateBox(textView: TextView) {
        textView.gone()
    }

    override fun isLastTextView(current: Int): Boolean {
        return current + 1 >= 7
    }

    override fun getPlate(plateList: List<TextView>): String {
        return plateList.joinToString(separator = "", limit = 7, truncated = "") { it.text }
    }
}