package com.zhan.plate

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.PopupWindow
import com.zhan.ktwing.ext.gone
import com.zhan.ktwing.ext.visible
import kotlinx.android.synthetic.main.layout_plate_input_view.view.*


/**
 *  @author:  hyzhan
 *  @date:    2019/7/22
 *  @desc:    TODO
 */
class PlateInputView(
    private val mContext: Activity,
    val listener: (word: String) -> Unit
) : PopupWindow(mContext) {

    companion object {
        const val DELETE = "delete"
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
        initPlateView()
    }


    private fun initPlateView() {

        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_plate_input_view, null)

        //设置宽与高
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.WRAP_CONTENT

        setBackgroundDrawable(ColorDrawable(Color.WHITE))

        isFocusable = false
        // animationStyle = R.style.MyPopUpWindow
        isOutsideTouchable = false
        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        isTouchable = true

        contentView.mIvClose.setOnClickListener { this.dismiss() }


        contentView.mKvProvinces.run {
            keyboard = Keyboard(mContext, R.xml.province_abbreviation)
            isEnabled = true
            //设置pop弹窗
            isPreviewEnabled = true
            setOnKeyboardActionListener(keyboardListener)
        }

        contentView.mKvNumberLetters.run {
            keyboard = Keyboard(mContext, R.xml.number_and_letters)
            isEnabled = true
            isPreviewEnabled = true
            setOnKeyboardActionListener(keyboardListener)
        }
    }

    fun showProvinces() {
        contentView.mKvProvinces.visible()
        contentView.mKvNumberLetters.gone()
    }

    fun showNumberLetters() {
        contentView.mKvProvinces.gone()
        contentView.mKvNumberLetters.visible()
    }
}