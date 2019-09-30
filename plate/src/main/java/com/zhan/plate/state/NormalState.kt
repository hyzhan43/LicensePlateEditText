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

    override fun displayPlateBox(plateView: PlateView, textView: TextView) {
        plateView.mTvHint.text = plateView.context.getString(R.string.change_to_new_plate)
        textView.gone()
    }

    override fun isLastTextView(current: Int): Boolean {
        return current + 1 >= 7
    }

    override fun getPlate(plateList: List<TextView>): String = plateList.joinToString(separator = "", limit = 7, truncated = "") { it.text }
}