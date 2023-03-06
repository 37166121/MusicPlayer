package com.aliyunm.common.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.Window


object ScreenUtils {

    /**
     * 全屏
     */
    fun fullScreen(window : Window) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            // 内容延申到状态栏和导航栏
            window.apply {
                setDecorFitsSystemWindows(false)
                statusBarColor = Color.TRANSPARENT
                navigationBarColor = Color.TRANSPARENT
            }
        } else {
            // 全屏显示，隐藏状态栏和导航栏，拉出状态栏和导航栏显示一会儿后消失。
            // window.decorView.systemUiVisibility = (
            //         View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //         or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //         or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            //         or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            //         or View.SYSTEM_UI_FLAG_FULLSCREEN
            //         or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId: Int = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(activity: Activity): Int {
        return activity.windowManager.defaultDisplay.height
    }

    /**
     * 获取导航栏高度
     */
    fun getNavigationBarHeight(context: Context): Int {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        val height: Int = resources.getDimensionPixelSize(resourceId)
        return height
    }

}