package com.zhan.plate.state

import android.widget.TextView
import com.zhan.plate.PlateView
import com.zhan.ktwing.ext.visible
import com.zhan.plate.R
import kotlinx.android.synthetic.main.layout_plate_view.view.*

/**
 *  @author:  hyzhan
 *  @date:    2019/7/23
 *  @desc:    新能源车牌
 */
class NewEnergyState : PlateState {

    override fun displayPlateBox(plateView: PlateView, textView: TextView) {
        plateView.mTvHint.text = plateView.context.getString(R.string.change_to_normal_plate)
        textView.visible()
    }

    override fun isLastTextView(current: Int): Boolean {
        return current + 1 >= 8
    }

    override fun getPlate(plateList: List<TextView>): String {
        return plateList.joinToString(separator = "", limit = 8, truncated = "") { it.text }
    }
}