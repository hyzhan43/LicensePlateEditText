package com.zhan.plate

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins
import com.zhan.ktwing.ext.getDrawableRef
import com.zhan.ktwing.ext.gone
import com.zhan.ktwing.ext.sp2px
import com.zhan.ktwing.ext.visible
import com.zhan.plate.enums.BackgroundStyleEnum
import com.zhan.plate.ext.isNewEnergyPlate
import com.zhan.plate.ext.isPlate
import com.zhan.plate.state.PlateManager
import kotlinx.android.synthetic.main.layout_plate_view.view.*
import kotlinx.android.synthetic.main.layout_plate_view.view.mKvNumberLetters
import kotlinx.android.synthetic.main.layout_plate_view.view.mKvProvinces

/**
 *  @author:  hyzhan
 *  @date:    2019/7/22
 *  @desc:    TODO
 */
class PlateView @JvmOverloads constructor(
    val mContext: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(mContext, attrs, defStyle) {

    // 默认显示文本
    private val defText = "-"
    // 默认字体大小
    private val defTextSize = 16f.sp2px
    private val textSize: Float

    // 默认统一颜色
    private val normalColor: Int
    private val focusColor: Int

    // 默认字体颜色
    private val normalTextColor: Int
    private val focusTextColor: Int

    private val backgroundStyle: Int

    // 默认背景颜色
    private val normalBgColor: Int
    private val focusBgColor: Int

    // 4px
    private val defaultMargin = 4
    private val marginSize: Int

    private val defTextNormalBg = getDrawableRef(R.drawable.shape_radius4_bg)
    private val defTextFocusBg = getDrawableRef(R.drawable.shape_radius4_bg)

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

    private val keyboardListener = object : KeyboardView.OnKeyboardActionListener {
        override fun swipeUp() {}
        override fun swipeRight() {}
        override fun swipeLeft() {}
        override fun swipeDown() {}
        override fun onText(text: CharSequence) {}
        override fun onRelease(primaryCode: Int) {}
        override fun onPress(primaryCode: Int) {}
        override fun onKey(primaryCode: Int, keyCodes: IntArray) {
//            val editable = mEdit.text
//            val start = mEdit.selectionStart
            //判定是否是中文的正则表达式 [\\u4e00-\\u9fa5]判断一个中文 [\\u4e00-\\u9fa5]+多个中文
            val reg = "[\\u4e00-\\u9fa5]"

            if (primaryCode == -3) {
//                if (editable != null && editable.isNotEmpty()) {
//                    //没有输入内容时软键盘重置为省份简称软键盘
//
//                    if (start > 0) {
//                        editable.delete(start - 1, start)
//                    }
//                }
            }
        }
    }

    init {
        LayoutInflater.from(mContext).inflate(R.layout.layout_plate_view, this)

        mContext.obtainStyledAttributes(attrs, R.styleable.PlateView, defStyle, 0).run {

            marginSize = getDimensionPixelSize(R.styleable.PlateView_marginSize, defaultMargin)

            textSize = getDimension(R.styleable.PlateView_textSize, defTextSize)

            backgroundStyle =
                getInt(R.styleable.PlateView_backgroundStyle, BackgroundStyleEnum.STROKE.code)

            focusColor = getColor(R.styleable.PlateView_focusColor, Color.BLACK)
            normalColor = getColor(R.styleable.PlateView_normalColor, Color.BLACK)

            focusTextColor = getColor(R.styleable.PlateView_focusTextColor, focusColor)
            normalTextColor = getColor(R.styleable.PlateView_normalTextColor, normalColor)

            focusBgColor = getColor(R.styleable.PlateView_focusBgColor, focusColor)
            normalBgColor = getColor(R.styleable.PlateView_normalBgColor, normalColor)

            recycle()
        }

        initView()
    }

    private fun initView() {
        initPlateChar()

        plateManager.displayPlateBox()

        initPlateInputView()
    }

    /**
     *  动态生成 车牌号码每一个 TextView
     */
    private fun initPlateChar() {

        initBackgroundStyle()

        val lp = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f).apply { setMargins(marginSize) }

        (0..total).forEach { index ->
            val plateText = generateCharText(index, lp)
            plateTextList.add(plateText)
            mLlContent.addView(plateText)
        }

        plateManager = PlateManager(plateTextList)
    }

    private fun initBackgroundStyle() {
        if (backgroundStyle == BackgroundStyleEnum.STROKE.code) {
            (defTextNormalBg as GradientDrawable).setStroke(1, normalBgColor)
            (defTextFocusBg as GradientDrawable).setStroke(1, focusBgColor)
        } else {
            (defTextNormalBg as GradientDrawable).setColor(normalBgColor)
            (defTextFocusBg as GradientDrawable).setColor(focusBgColor)
        }
    }

    private fun showPlateInputView() {

        //mPlateInputView.showAtLocation(this, Gravity.BOTTOM, 0, 0)
        when (currentIndex) {
            0 -> showProvinces()
            else -> showNumberLetters()
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
        textView.setTextColor(focusTextColor)
    }

    /**
     *  重置textView 样式
     */
    private fun resetTextViewStyle(textView: TextView) {
        textView.setTextColor(normalTextColor)
        textView.background = defTextNormalBg
    }

    private fun isFocus(index: Int) = index == currentIndex

    private fun generateCharText(index: Int, lp: LayoutParams): TextView {
        return TextView(context).apply {
            paint.isFakeBoldText = true
            text = defText
            setTextSize(TypedValue.COMPLEX_UNIT_PX, defTextSize)
            setTextColor(normalTextColor)
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

//        mPlateInputView = PlateInputView(mContext as Activity) { word ->
//
//            // 点击删除按钮
//            if (isDeleteButton(word)) {
//                plateCharBack(plateTextList[currentIndex])
//                return@PlateInputView
//            }
//
//            plateCharForward(plateTextList[currentIndex], word)
//        }

        mKvProvinces.run {
            keyboard = Keyboard(mContext, R.xml.province_abbreviation)
            isEnabled = true
            //设置pop弹窗
            isPreviewEnabled = true
            setOnKeyboardActionListener(keyboardListener)
        }

        mKvNumberLetters.run {
            keyboard = Keyboard(mContext, R.xml.number_and_letters)
            isEnabled = true
            isPreviewEnabled = true
             setOnKeyboardActionListener(keyboardListener)
        }
    }

    fun showProvinces() {
        mKvProvinces.visible()
        mKvNumberLetters.gone()
    }

    fun showNumberLetters() {
        mKvProvinces.gone()
        mKvNumberLetters.visible()
    }

    private fun isDeleteButton(word: String): Boolean = word == PlateInputView.DELETE

    private fun plateCharForward(textView: TextView, content: String) {

        if (isFirstChar()) {
//            mPlateInputView.showNumberLetters()
        }

        resetTextViewStyleAndText(textView, content)

        if (plateManager.isLastTextView(currentIndex)) {
            mPlateInputView.dismiss()
            return
        }

        currentIndex++
        setTextViewFocusStyle(plateTextList[currentIndex])
    }

    private fun isFirstChar() = currentIndex == 0

    private fun plateCharBack(textView: TextView) {
        if (isSecondChar()) {
//            mPlateInputView.showProvinces()
        }

        resetTextViewStyleAndText(textView, defText)

        if (isZeroChar()) {
            return
        }

        currentIndex--
        setTextViewFocusStyle(plateTextList[currentIndex])
    }

    private fun resetTextViewStyleAndText(textView: TextView, content: String) {
        textView.text = content
        resetTextViewStyle(textView)
    }

    private fun isZeroChar() = currentIndex - 1 < 0

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

    fun setTriggerView(view: View) {
        view.setOnClickListener {
            plateManager.switchPlateState()
            plateManager.displayPlateBox()
        }
    }


}