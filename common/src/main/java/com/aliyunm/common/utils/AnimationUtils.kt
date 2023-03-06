package com.aliyunm.common.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation

object AnimationUtils {

    enum class Translate {
        H,
        V
    }

    interface ProgressListener {
        fun progress(progress : Float)
    }

    class Build {
        private var mStart : Float = 0f
        private var mEnd : Float = 0f
        private var mDuration : Long = 0
        private var mInterpolator : Interpolator = LinearInterpolator()
        private lateinit var valueAnimator : ValueAnimator
        private lateinit var progressListener : ProgressListener

        fun setStart(start : Float) : Build {
            this.mStart = start
            return this
        }

        fun setEnd(end : Float) : Build {
            this.mEnd = end
            return this
        }

        fun setDuration(duration : Long) : Build {
            this.mDuration = duration
            return this
        }

        fun setInterpolator(interpolator : Interpolator): Build {
            this.mInterpolator = interpolator
            return this
        }

        fun setProgressListener(progressListener : ProgressListener) : Build {
            this.progressListener = progressListener
            return this
        }

        fun startAnimator() {
            valueAnimator = build().apply {
                start()
            }
        }

        fun build() : ValueAnimator {
            return ValueAnimator.ofFloat(mStart, mEnd).apply {
                duration = mDuration
                interpolator = mInterpolator
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
                addUpdateListener { animation ->
                    if (this@Build::progressListener.isInitialized) {
                        progressListener.progress(animation.animatedValue as Float)
                    }
                }
            }
        }

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