package com.zhan.plate.state

import android.widget.TextView
import com.zhan.plate.PlateView

/**
 *  @author:  hyzhan
 *  @date:    2019/7/23
 *  @desc:    TODO
 */
class PlateManager(private val plateView: PlateView, private val charTextViews: List<TextView>) {

    // 默认状态
    var state: PlateState = NormalState()

    fun switchPlateState() {
        state = if (state is NormalState) {
            NewEnergyState()
        } else {
            NormalState()
        }
    }

    fun displayPlateBox() {
        state.displayPlateBox(plateView, charTextViews[7])
    }

    fun isLastTextView(current: Int): Boolean {
        return state.isLastTextView(current)
    }

    fun getPlate(): String {
        return state.getPlate(charTextViews)
    }
}