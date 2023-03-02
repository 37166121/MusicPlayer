package com.aliyunm.common

import android.widget.ImageView
import com.aliyunm.common.utils.GlideUtils

fun ImageView.setImage(path : String, type : Int = 0, radian : Int = 4) {
    GlideUtils.setImage(context, path, this, type, radian)
}