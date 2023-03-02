package com.aliyunm.musicplayer.popup

import android.view.Gravity
import android.view.View
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding
import com.aliyunm.musicplayer.R

abstract class BaseBottomPopup<VB : ViewBinding>(private val activity: ComponentActivity) : BasePopup<VB>(activity) {

    init {
        animationStyle = R.style.PopupWindowAnimation
    }

    override fun show() {
        super.show()
        showAtLocation(View(getActivity()), Gravity.BOTTOM, 0, 0)
    }

    override fun show(w : Int, h : Int) {
        super.show(w, h)
        showAtLocation(View(getActivity()), Gravity.BOTTOM, 0, 0)
    }
}