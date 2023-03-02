package com.aliyunm.common.utils

import android.content.res.Resources

object DisplayUtils {

    private val density = Resources.getSystem().displayMetrics.density

    fun dp2px(dp : Float): Int {
        return (0.5f + dp * density).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(px: Float): Float {
        return px / density
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(dp: Float): Int {
        return (0.5f + dp * density).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(px: Float): Float {
        return px / density
    }
}