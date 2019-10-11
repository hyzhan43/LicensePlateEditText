package com.zhan.licenseplateedittext

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import com.zhan.ktwing.ext.visible
import com.zhan.plate.PlateInputView
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private lateinit var plateInputView: PlateInputView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mPlateView.plate = "粤A11111"
        mPlateView.setTriggerView(mTvHint)


//        plateInputView = PlateInputView(this) {
//
//        }

//        mKvProvinces.run {
//            keyboard = Keyboard(this@MainActivity, R.xml.province_abbreviation)
//            isEnabled = true
//            //设置pop弹窗
//            isPreviewEnabled = true
//            setOnKeyboardActionListener(object : KeyboardView.OnKeyboardActionListener {
//                override fun swipeUp() {}
//                override fun swipeRight() {}
//                override fun swipeLeft() {}
//                override fun swipeDown() {}
//                override fun onText(text: CharSequence) {}
//                override fun onRelease(primaryCode: Int) {}
//                override fun onPress(primaryCode: Int) {}
//                override fun onKey(primaryCode: Int, keyCodes: IntArray) {
//                    //判定是否是中文的正则表达式 [\\u4e00-\\u9fa5]判断一个中文 [\\u4e00-\\u9fa5]+多个中文
//                    val reg = "[\\u4e00-\\u9fa5]"
//
//                    Log.d("LST", "primaryCode = $primaryCode")
//                }
//            })
//        }
//
//        id_keyboard.setOnClickListener {
//            mKvProvinces.visible()
//        }
    }

//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        plateInputView.showNumberLetters()
//        plateInputView.showAtLocation(mLlContainer, Gravity.BOTTOM, 0, 0)
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        plateInputView.dismiss()
//    }
}
