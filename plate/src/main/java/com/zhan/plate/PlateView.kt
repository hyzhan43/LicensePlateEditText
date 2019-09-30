package com.zhan.plate

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins
import com.zhan.ktwing.ext.getDrawableRef
import com.zhan.ktwing.ext.sp2px
import com.zhan.plate.ext.isNewEnergyPlate
import com.zhan.plate.ext.isPlate
import com.zhan.plate.state.PlateManager
import kotlinx.android.synthetic.main.layout_plate_view.view.*

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
    private val textSize: Float

    // 默认字体颜色
    private val normalColor: Int
    private var focusColor: Int

    // 4px
    private val defaultMargin = 4
    private val marginSize: Int

    private val defTextNormalBg = getDrawableRef(R.drawable.rect_stroke_primary)
    private val defTextFocusBg = getDrawableRef(R.drawable.rect_solid_primary)

    private var plateTextList = arrayListOf<TextView>()

    // 当前选中的 索引
    private var currentIndex = -1

    private lateinit var plateManager: PlateManager

    private lateinit var mPlateInputView: PlateInputView

    private val total = 7

    var plate: String = ""
        get() = plateManager.getPlate()
        set(value) {
            field = value
            initPlate(value)
        }


    init {
        LayoutInflater.from(context).inflate(R.layout.layout_plate_view, this)

        context.obtainStyledAttributes(attrs, R.styleable.PlateView, defStyle, 0).run {

            marginSize = getDimensionPixelSize(R.styleable.PlateView_marginSize, defaultMargin)

            textSize = getDimension(R.styleable.PlateView_textSize, defTextSize)
            focusColor = getColor(R.styleable.PlateView_focusColor, Color.WHITE)
            normalColor = getColor(R.styleable.PlateView_normalColor, Color.BLACK)

            recycle()
        }

        initView()
    }

    private fun initView() {
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

        val lp = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f).apply { setMargins(marginSize) }

        (0..total).forEach { index ->
            val plateText = generateCharText(index, lp)
            plateTextList.add(plateText)
            // 添加到 LinearLayout 中去。
            mLlContent.addView(plateText)
        }

        plateManager = PlateManager(this, plateTextList)
    }

    private fun showPlateInputView() {
        mPlateInputView.showAtLocation(this, Gravity.BOTTOM, 0, 0)
        when (currentIndex) {
            0 -> mPlateInputView.showProvince()
            else -> mPlateInputView.showPlateChar()
        }
    }

    private fun changeStyle() {
        plateTextList.forEachIndexed { index, textView ->
            if (isFocus(index)) {
                setTextViewFocusStyle(textView)
            } else {
                resetTextViewStyle(textView)
            }
        }
    }

    /**
     * 设置textView选中样式
     */
    private fun setTextViewFocusStyle(textView: TextView) {
        textView.background = defTextFocusBg
        textView.setTextColor(focusColor)
    }

    /**
     *  重置textView 样式
     */
    private fun resetTextViewStyle(textView: TextView) {
        textView.setTextColor(normalColor)
        textView.background = defTextNormalBg
    }

    private fun isFocus(index: Int) = index == currentIndex

    private fun generateCharText(index: Int, lp: LayoutParams): TextView {
        return TextView(context).apply {
            text = defText
            setTextSize(TypedValue.COMPLEX_UNIT_PX, defTextSize)
            setTextColor(normalColor)
            gravity = Gravity.CENTER
            layoutParams = lp
            background = defTextNormalBg
            tag = index
            setOnClickListener { showInputView(it) }
        }
    }

    private fun showInputView(it: View) {
        currentIndex = it.tag as Int
        showPlateInputView()
        changeStyle()
    }

    private fun initPlateInputView() {
        mPlateInputView = PlateInputView(context) { word ->

            // 点击删除按钮
            if (isDeleteButton(word)) {
                plateTextList[currentIndex].text = defText
                plateCharBack()
                return@PlateInputView
            }

            plateTextList[currentIndex].text = word
            plateCharForward()
        }
    }

    private fun isDeleteButton(word: String): Boolean = word == PlateInputView.DELETE

    private fun plateCharForward() {

        if (isFirstChar()) {
            mPlateInputView.showPlateChar()
        }

        resetTextViewStyle(plateTextList[currentIndex])

        if (plateManager.isLastTextView(currentIndex)) {
            mPlateInputView.dismiss()
            return
        }

        currentIndex++

        setTextViewFocusStyle(plateTextList[currentIndex])
    }

    private fun isFirstChar() = currentIndex == 0

    private fun plateCharBack() {

        if (isSecondChar()) {
            mPlateInputView.showProvince()
        }

        resetTextViewStyle(plateTextList[currentIndex])

        if (currentIndex - 1 < 0) {
            return
        }

        currentIndex--
        setTextViewFocusStyle(plateTextList[currentIndex])
    }

    private fun isSecondChar() = currentIndex == 1

    /**
     *  设置车牌
     */
    private fun initPlate(plate: String) {
        require(!(plate.isEmpty() || !plate.isPlate())) { "please input right plate" }

        if (plate.isNewEnergyPlate()) {
            plateManager.switchPlateState()
        }

        plateManager.setupPlate(plate)
    }
}