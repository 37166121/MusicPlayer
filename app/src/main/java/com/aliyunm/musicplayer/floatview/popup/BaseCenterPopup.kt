package com.aliyunm.musicplayer.floatview.popup

import android.view.Gravity
import android.view.View
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding

abstract class BaseCenterPopup<VB : ViewBinding>(activity: ComponentActivity) : BasePopup<VB>(activity) {
    override fun show(isDark : Boolean) {
        super.show(isDark)
        showAtLocation(View(getActivity()), Gravity.CENTER, 0, 0)
    }
}