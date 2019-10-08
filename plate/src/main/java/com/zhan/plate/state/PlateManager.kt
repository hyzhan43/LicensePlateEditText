package com.zhan.plate.state

import android.widget.TextView
import com.zhan.plate.PlateView
import java.util.*

/**
 *  @author:  hyzhan
 *  @date:    2019/7/23
 *  @desc:    plateView 管理类
 */
class PlateManager(private val charTextViews: List<TextView>) {

    // 默认状态
    var state: PlateState = NormalState()

    /**
     *  切换状态
     */
    fun switchPlateState() {
        state = if (state is NormalState) {
            NewEnergyState()
        } else {
            NormalState()
        }
    }


    fun displayPlateBox() {
        state.displayPlateBox(charTextViews[7])
    }

    fun isLastTextView(current: Int): Boolean {
        return state.isLastTextView(current)
    }

    fun getPlate(): String {
        return state.getPlate(charTextViews)
    }

    fun setupPlate(plate: String) {

        val length = plate.length - 1
        for (index in 0..length) {
            charTextViews[index].text = plate[index].toString().toUpperCase(Locale.CHINA)
        }

        displayPlateBox()
    }
}