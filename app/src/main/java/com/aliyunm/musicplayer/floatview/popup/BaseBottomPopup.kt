package com.aliyunm.musicplayer.floatview.popup

import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding
import com.aliyunm.musicplayer.R

abstract class BaseBottomPopup<VB : ViewBinding>(activity: ComponentActivity) : BasePopup<VB>(activity) {

    private var oldY: Int = 0
    private var offsetY: Int = 0

    /**
     * 滑动关闭阈值
     */
    private var threshold: Int = 4

    private val onTouchListener : View.OnTouchListener = View.OnTouchListener { v, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN     -> {
                oldY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE     -> {
                offsetY = ((event.rawY - oldY).toInt())
                if (offsetY < 0) {
                    offsetY = 0
                }
                viewBinding.root.y = offsetY.toFloat()
                viewBinding.root.invalidate()
            }
            MotionEvent.ACTION_UP       -> {
                return@OnTouchListener up()
            }
            MotionEvent.ACTION_CANCEL   -> {
                return@OnTouchListener up()
            }
        }

        // 没有performClick会有警告
        // 用@SuppressLint("ClickableViewAccessibility") 或者if (false) 屏蔽掉
        if (false) {
            v.performClick()
        }

        return@OnTouchListener false
    }

    private fun up() : Boolean {
        if (offsetY == 0) {
            return false
        }
        if (offsetY > viewBinding.root.height / threshold) {
            dismiss()
        } else {
            // 回弹动画
            rebound()
        }
        return true
    }

    init {
        animationStyle = R.style.PopupWindowAnimation
        setTouchInterceptor(onTouchListener)
    }

    /**
     * 滑动未超过阈值回弹
     */
    private fun rebound() {
        val animation = TranslateAnimation(0f, 0f, 0f, 0f - offsetY)
        animation.duration = 200
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                viewBinding.root.clearAnimation()
                viewBinding.root.y = 0f
                viewBinding.root.invalidate()
                offsetY = 0
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        viewBinding.root.startAnimation(animation)
    }

    override fun show(isDark : Boolean) {
        super.show(isDark)
        showAtLocation(View(getActivity()), Gravity.BOTTOM, 0, 0)
    }

    override fun show(w : Int, h : Int, isDark : Boolean) {
        super.show(w, h, isDark)
        viewBinding.root.y = 0f
        viewBinding.root.invalidate()
        showAtLocation(View(getActivity()), Gravity.BOTTOM, 0, 0)
    }

    override fun dismiss() {
        super.dismiss()
        // 重置位置
        oldY = 0
        offsetY = 0
    }
}