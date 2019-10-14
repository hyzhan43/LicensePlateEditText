# LicensePlateEditText

[![](https://jitpack.io/v/hyzhan43/LicensePlateEditText.svg)](https://jitpack.io/#hyzhan43/LicensePlateEditText)


`LicensePlateEditText` 是Android 车牌输入框控件效果图如下图所示

![省份](https://github.com/hyzhan43/LicensePlateEditText/blob/master/images/img_provinces.png)

![数字/字母](https://github.com/hyzhan43/LicensePlateEditText/blob/master/images/img_number_letters.png)


# Step1
```java
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

# Step2
```java
dependencies {
	implementation 'com.github.hyzhan43:LicensePlateEditText:1.0.0'
}
```

# Step3 

```kotlin
<com.zhan.plate.PlateView
        android:id="@+id/mPlateView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="10dp"
        app:focusColor="@color/colorPrimary"/>
```

在xml 中声明  PlateView 控件

```kotlin
package com.zhan.licenseplateedittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPlateView.plate = "粤A11111"
        // 绑定 切换新能源车牌 View
        mPlateView.setTriggerView(mTvHint)
    }
}

```

如需新能源车牌，则需要绑定 切换新能源车牌 View。调用 `setTriggerView` 方法.

# 更多用法

## Attributes属性（banner布局文件中调用）
|Attributes     | Format     | Describe
|---            | ---        | ---|
focusColor      | color      | 设置选中输入框整体(字体+背景)颜色 默认Color.BLACK
normalColor     | color      | 设置正常输入框整体(字体+背景)颜色 默认Color.BLACK
focusTextColor  | color      | 设置选中字体颜色 默认Color.BLACK, 如果设置了 focusColor, 优先显示focusColor颜色
normalTextColor | color      | 设置正常字体颜色 默认Color.BLACK, 如果设置了 normalColor, 优先显示normalColor颜色
focusBgColor    | color      | 设置选中输入框背景颜色 默认Color.BLACK, 如果设置了 focusColor, 优先显示focusColor颜色
normalBgColor   | color      | 设置正常输入框背景颜色 默认Color.BLACK, 如果设置了 focusColor, 优先显示focusColor颜色
backgroundStyle | enum       | 设置输入框填充样式, Stroke(边框, 默认)、Fill(填充)
borderWidth     | dimension  | 设置输入框边框大小
textSize        | dimension  | 设置输入框字体大小 默认 16sp        