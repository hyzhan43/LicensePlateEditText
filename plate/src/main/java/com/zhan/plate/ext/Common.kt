package com.zhan.plate.ext

import java.util.regex.Pattern

/**
 *  @author:  hyzhan
 *  @date:    2019/9/27
 *  @desc:    车牌正则校验
 */
fun String.isPlate(): Boolean {
    /**
     * 判断是否是车牌号码
     * 新能源车
     * 组成：省份简称（1位汉字）+发牌机关代号（1位字母）+序号（6位），总计8个字符，序号不能出现字母I和字母O
     * 通用规则：不区分大小写，第一位：省份简称（1位汉字），第二位：发牌机关代号（1位字母）
     * 序号位：
     * 小型车，第一位：只能用字母D或字母F，第二位：字母或者数字，后四位：必须使用数字
     * 大型车，前五位：必须使用数字，第六位：只能用字母D或字母F。
     * 新能源：[\u4e00-\u9fa5][a-zA-Z](([DF](?![a-zA-Z0-9]*[IO])[0-9]{4})|([0-9]{5}[DF]))
     */
    return this.isNormalPlate() || this.isNewEnergyPlate()
}

fun String.isNewEnergyPlate(): Boolean {

    if (this.length != 8) {
        return false
    }

    val pattern =
        "[\\u4e00-\\u9fa5][a-zA-Z](([DF]([a-hj-np-zA-HJ-NP-Z0-9])[0-9]{4})|([0-9]{5}[DF]))"
    return Pattern.compile(pattern).matcher(this).matches()
}


fun String.isNormalPlate(): Boolean {

    if (this.length != 7) {
        return false
    }

    val pattern =
        "^[冀豫云辽黑湘皖鲁苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼渝京津沪新京军空海北沈兰济南广成使领]{1}[a-zA-Z0-9]{5}[a-zA-Z0-9挂学警港澳领]{1}$"
    return Pattern.compile(pattern).matcher(this).matches()
}