package com.zhan.plate

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.zhan.plate.state.PlateManager
import com.zhan.ktwing.ext.getDrawableRef
import com.zhan.ktwing.ext.sp2px
import com.zhan.plate.ext.isPlate
import kotlinx.android.synthetic.main.layout_plate_view.view.*
import java.util.*

/**
 *  @author:  hyzhan
 *  @date:    2019/7/22
 *  @desc:    TODO
 */
class PlateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    // 默认显示文本
    private val defText = "-"
    // 默认字体大小
    private val defTextSize = 16f.sp2px
    // 默认字体颜色
    private val normalColor = Color.BLACK
    private var focusColor = Color.WHITE

    private val defTextNormalBg = getDrawableRef(R.drawable.rect_stroke_primary)
    private val defTextFocusBg = getDrawableRef(R.drawable.rect_solid_primary)

    private var plateTextList = arrayListOf<TextView>()

    // 当前选中的 索引
    private var currentIndex = -1

    private lateinit var plateManager: PlateManager

    private lateinit var mPlateInputView: PlateInputView

    private val total = 7

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_plate_view, this)

        initEachPlateChar()

        plateManager.displayPlateBox()

        initPlateInputView()

        mTvHint.setOnClickListener {
            plateManager.switchPlateState()
            plateManager.displayPlateBox()
        }
    }

    /**
     *  动态生成 车牌号码每一个 TextView
     */
    private fun initEachPlateChar() {

        (0..total).forEach { index ->
            val plateText = generateCharText(index)
            plateTextList.add(plateText)
            // 添加到 LinearLayout 中去。
            mLlContent.addView(plateText)
        }

        plateManager = PlateManager(this, plateTextList)
    }

    private fun showPlateInputView() {
        when (currentIndex) {
            0 -> mPlateInputView.showProvince()
            else -> mPlateInputView.showPlateChar()
        }
    }

    private fun changeStyle() {
        plateTextList.forEachIndexed { index, textView ->
            if (index == currentIndex) {
                // 设置选中样式
                textView.run {
                    background = defTextFocusBg
                    setTextColor(focusColor)
                }
            } else {
                // 设置选中样式
                textView.run {
                    setTextColor(normalColor)
                    background = defTextNormalBg
                }
            }
        }
    }

    private fun generateCharText(index: Int): TextView {
        val lp = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f)
        return TextView(context).apply {
            text = defText
            setTextSize(TypedValue.COMPLEX_UNIT_PX, defTextSize)
            setTextColor(normalColor)
            gravity = Gravity.CENTER
            layoutParams = lp
            background = defTextNormalBg
            tag = index
            setOnClickListener {
                currentIndex = it.tag as Int

                showPlateInputView()
                changeStyle()

                mPlateInputView.showAtLocation(
                    this,
                    Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                    0,
                    0
                )
            }
        }
    }

    private fun initPlateInputView() {
        mPlateInputView = PlateInputView(context) { word ->

            // 点击删除按钮
            if (word == PlateInputView.DELETE) {
                plateTextList[currentIndex].text = defText
                plateCharBack()
                return@PlateInputView
            }
            plateTextList[currentIndex].text = word
            plateCharForward()
        }
    }

    private fun plateCharForward() {

        if (currentIndex == 0) {
            mPlateInputView.showPlateChar()
        }

        plateTextList[currentIndex].run {
            setTextColor(normalColor)
            background = defTextNormalBg
        }

        if (plateManager.isLastTextView(currentIndex)) {
            mPlateInputView.dismiss()
            return
        }

        currentIndex++

        plateTextList[currentIndex].run {
            setTextColor(focusColor)
            background = defTextFocusBg
        }
    }

    private fun plateCharBack() {

        if (currentIndex == 1) {
            mPlateInputView.showProvince()
        }

        plateTextList[currentIndex].run {
            setTextColor(normalColor)
            background = defTextNormalBg
        }

        if (currentIndex - 1 < 0) {
            return
        }

        currentIndex--

        plateTextList[currentIndex].run {
            setTextColor(focusColor)
            background = defTextFocusBg
        }
    }

    fun getPlate(): String = plateManager.getPlate()

    /**
     *  设置车牌
     */
    fun setPlate(plate: String) {
        if (plate.isEmpty() || !plate.isPlate()) {
            return
        }

        val length = plate.length - 1
        for (index in 0..length) {
            plateTextList[index].text = plate[index].toString().toUpperCase(Locale.CHINA)
        }

        if (length != 6) {
            plateManager.switchPlateState()
            plateManager.displayPlateBox()
        }
    }
}