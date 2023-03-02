package com.aliyunm.common.utils

import android.view.animation.Animation
import android.view.animation.TranslateAnimation

object AnimationUtils {

    enum class Translate {
        H,
        V
    }

    fun translateAnimation(type : Translate, start : Float, end : Float, repeatMode : Int = Animation.REVERSE, duration : Long = 1000): TranslateAnimation {
        return when(type) {
            Translate.H -> {
                translateAnimation(start, end, 0f, 0f, repeatMode, duration)
            }

            Translate.V -> {
                translateAnimation(0f, 0f, start, end, repeatMode, duration)
            }
        }
    }

    fun translateAnimation(startX: Float, endX: Float, startY: Float, endY: Float, repeatMode : Int = Animation.REVERSE, duration : Long = 1000): TranslateAnimation {
        return TranslateAnimation(
            Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
            startX,//fromXValue表示开始的X轴位置
            Animation.RELATIVE_TO_SELF,
            endX,//fromXValue表示结束的X轴位置
            Animation.RELATIVE_TO_SELF,
            startY,//fromXValue表示开始的Y轴位置
            Animation.RELATIVE_TO_SELF,
            endY
        ).apply {
            this.repeatMode = repeatMode
            this.duration = duration
        }
    }
}