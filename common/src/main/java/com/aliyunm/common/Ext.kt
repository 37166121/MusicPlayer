package com.aliyunm.common

import android.graphics.Bitmap
import android.widget.ImageView
import com.aliyunm.common.utils.BitmapUtils
import com.aliyunm.common.utils.GlideUtils

fun ImageView.setImage(path: Any, type: Int = 0, radian: Int = 4) {
    GlideUtils.setImage(context, path, this, type, radian)
}

fun ImageView.setBlurTransformation(bitmap: Bitmap, blurRadius : Float = 50f) {
    GlideUtils.setBlurTransformation(context, bitmap, this)
    BitmapUtils.blur(bitmap, this, blurRadius)
}