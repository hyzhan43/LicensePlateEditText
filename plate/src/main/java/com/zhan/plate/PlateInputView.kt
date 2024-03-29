package com.zhan.plate

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.recyclerview.widget.GridLayoutManager
import com.zhan.ktwing.ext.gone
import com.zhan.ktwing.ext.visible
import com.zhan.plate.adapter.CharAdapter
import com.zhan.plate.adapter.ProvinceAdapter
import com.zhan.plate.bean.PlateItem
import kotlinx.android.synthetic.main.layout_plate_input_view.view.*


/**
 *  @author:  hyzhan
 *  @date:    2019/7/22
 *  @desc:    TODO
 */
class PlateInputView(
    private val context: Context,
    val listener: (word: String) -> Unit
) : PopupWindow(context) {

    companion object {
        const val DELETE = "delete"
    }

    private val charAdapter by lazy { CharAdapter() }
    private val provinceAdapter by lazy { CharAdapter() }

    private val chinese = arrayOf("港", "澳", "学", "领")
    private val num = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    //    private val letters = arrayOf(
//        "A", "B", "C", "D", "E",
//        "F", "G", "H", "J", "K",
//        "L", "M", "N", "O", "P",
//        "Q", "R", "S", "T", "U",
//        "V", "W", "X", "Y", "Z"
//    )
    private val letters = arrayOf(
        "Q", "W", "E", "R", "T",
        "Y", "U", "I", "O", "P",
        "A", "S", "D", "F", "G",
        "H", "J", "K", "L", "Z",
        "X", "C", "V", "B", "N", "M"
    )

    private val provinces = arrayOf(
        "粤", "京", "沪", "津", "浙",
        "苏", "湘", "赣", "鄂", "豫",
        "皖", "陕", "甘", "宁", "云",
        "贵", "川", "渝", "黑", "吉",
        "辽", "晋", "冀", "鲁", "闽",
        "琼", "桂", "蒙", "青", "藏",
        "新"
    )

    private val charItems by lazy { initCharItemData() }
    private val provinceItems by lazy { initProvinceItemData() }

    init {
        initPlateView()
        initData()
        initRecyclerView()
    }

    private fun initPlateView() {
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_plate_input_view, null)

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
    }

    private fun initRecyclerView() {


        contentView.mRvContent.run {
            adapter = charAdapter
            layoutManager = GridLayoutManager(context, 10).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int = getSpanCount(position)
                }
            }

        }

        contentView.mRvProvince.run {
            layoutManager = GridLayoutManager(context, 10)
            adapter = provinceAdapter
        }

        charAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.mIvDelete -> listener(DELETE)
                R.id.mTvWord -> listener(charAdapter.data[position].data)
            }
        }

        provinceAdapter.setOnItemChildClickListener { _, _, position ->
            listener(provinceAdapter.data[position].data)
        }
    }

    private fun getSpanCount(position: Int): Int {
        val type = charAdapter.getItemViewType(position)
        return if (type == PlateItem.DELETE) {
            return 2
        } else {
            1
        }
    }

    private fun initData() {
        provinceAdapter.setNewData(provinceItems)
        charAdapter.setNewData(charItems)
    }

    private fun initCharItemData(): ArrayList<PlateItem> {
        return ArrayList<PlateItem>().apply {
            num.forEach { add(PlateItem(PlateItem.CHAR, it)) }

            letters.forEach { add(PlateItem(PlateItem.CHAR, it)) }

            (0..3).forEach { _ -> add(PlateItem(PlateItem.EMPTY)) }
            chinese.forEach { add(PlateItem(PlateItem.CHAR, it)) }
            (0..3).forEach { _ -> add(PlateItem(PlateItem.EMPTY)) }
            add(PlateItem(PlateItem.DELETE))
        }
    }

    private fun initProvinceItemData(): ArrayList<PlateItem> {
        return ArrayList<PlateItem>().apply {
            (0..9).forEach { add(PlateItem(PlateItem.CHAR, provinces[it])) }
            add(PlateItem(PlateItem.EMPTY))
            (11..18).forEach { add(PlateItem(PlateItem.CHAR, provinces[it])) }
            add(PlateItem(PlateItem.EMPTY))
            add(PlateItem(PlateItem.EMPTY))
            (23..30).forEach { add(PlateItem(PlateItem.CHAR, provinces[it])) }
        }
    }

    fun showProvince() {
        contentView.mRvProvince.visible()
        contentView.mRvContent.gone()
    }

    fun showPlateChar() {
        contentView.mRvProvince.gone()
        contentView.mRvContent.visible()
    }
}