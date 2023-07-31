package com.aliyunm.common.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.Window

object ScreenUtils {

    /**
     * 全屏
     */
    fun fullScreen(window : Window) {

    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    fun getStatusBarHeight(context : Context) : Int {
        // 获得状态栏高度
        val resourceId : Int =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(activity : Activity) : Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getRealMetrics(dm)
        return dm.heightPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenWidth(activity : Activity) : Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    /**
     * 获取导航栏高度
     */
    fun getNavigationBarHeight(context : Context) : Int {
        val resources : Resources = context.resources
        val resourceId : Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        val height : Int = resources.getDimensionPixelSize(resourceId)
        return height
    }

}