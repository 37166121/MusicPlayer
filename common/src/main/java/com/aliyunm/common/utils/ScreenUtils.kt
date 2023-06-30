package com.aliyunm.common.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat

object ScreenUtils {

    /**
     * 全屏
     */
    fun fullScreen(window: Window) {
        // <!-- Status bar color. -->
        // <item name="android:statusBarColor">@android:color/transparent</item>
        // <item name="android:navigationBarColor">@android:color/transparent</item>
        // <!-- Customize your theme here. -->
        // <item name="android:windowTranslucentNavigation">true</item>
        // <item name="android:windowTranslucentStatus">true</item>
        //
        // <item name="android:windowSoftInputMode">stateHidden|adjustPan</item>
        //
        // <item name="android:windowLightStatusBar">true</item>
        // <item name="android:windowLightNavigationBar">true</item>
        // <item name="android:windowContentOverlay">@null</item>
        // <item name="android:fitsSystemWindows">true</item>
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId: Int =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(activity: Activity): Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getRealMetrics(dm)
        return dm.heightPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenWidth(activity: Activity): Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
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