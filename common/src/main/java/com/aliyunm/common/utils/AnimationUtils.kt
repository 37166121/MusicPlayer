package com.aliyunm.common.utils

import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation

object AnimationUtils {

    enum class Translate {
        /**
         * 横向平移
         */
        H,

        /**
         * 纵向平移
         */
        V
    }

    interface ProgressListener {
        /**
         * 进度
         * @param progress 进度
         */
        fun progress(progress : Float)
    }

    /**
     * 属性动画
     */
    class BuildValueAnimator {
        /**
         * 动画参数类型
         */
        private var mType : Type = Type.INT
        private var mIntValues : IntArray = intArrayOf()
        private var mFloatValues : FloatArray = floatArrayOf()
        private var mObjectValues : Array<out Any> = arrayOf()
        private var mPropertyValues : Array<out PropertyValuesHolder> = arrayOf()

        /**
         * 动画播放时长
         */
        private var mDuration : Long = 0

        /**
         * 动画拦截器
         */
        private var mInterpolator : Interpolator = LinearInterpolator()

        /**
         * 自定义赋值器
         */
        private lateinit var mEvaluator : TypeEvaluator<Any?>

        /**
         * 进度监听器
         */
        private lateinit var progressListener : ProgressListener
        private lateinit var mValueAnimator : ValueAnimator

        /**
         * 动画参数类型
         */
        enum class Type {
            /**
             * 整型参数
             */
            INT,

            /**
             * 浮点参数
             */
            FLOAT,

            /**
             * 对象参数
             */
            OBJECT,

            /**
             * 并行动画参数
             */
            PROPERTYVALUESHOLDER,

            /**
             * 颜色参数
             */
            ARGB
        }

        fun setIntValues(vararg values : Int, type : Type = Type.INT) : BuildValueAnimator {
            mType = type
            mIntValues = values
            return this
        }

        fun setFloatValues(vararg values : Float, type : Type = Type.FLOAT) : BuildValueAnimator {
            mType = type
            mFloatValues = values
            return this
        }

        fun setObjectValues(vararg values : Any, type : Type = Type.OBJECT) : BuildValueAnimator {
            mType = type
            mObjectValues = values
            return this
        }

        fun setPropertyValues(vararg values : PropertyValuesHolder, type : Type = Type.PROPERTYVALUESHOLDER) : BuildValueAnimator {
            mType = type
            mPropertyValues = values
            return this
        }

        fun setDuration(duration : Long) : BuildValueAnimator {
            this.mDuration = duration
            return this
        }

        fun setInterpolator(interpolator : Interpolator) : BuildValueAnimator {
            this.mInterpolator = interpolator
            return this
        }

        fun setProgressListener(progressListener : ProgressListener) : BuildValueAnimator {
            this.progressListener = progressListener
            return this
        }

        fun setEvaluator(evaluator : TypeEvaluator<Any?>) : BuildValueAnimator {
            mEvaluator = evaluator
            return this
        }

        fun ofInt() : BuildValueAnimator {
            mType = Type.INT
            return this
        }

        fun ofFloat() : BuildValueAnimator {
            mType = Type.FLOAT
            return this
        }

        fun ofObject() : BuildValueAnimator {
            mType = Type.OBJECT
            return this
        }

        fun ofPropertyValuesHolder() : BuildValueAnimator {
            mType = Type.PROPERTYVALUESHOLDER
            return this
        }

        fun ofArgb() : BuildValueAnimator {
            mType = Type.ARGB
            return this
        }

        /**
         * 生成ValueAnimator并配置参数
         */
        fun build() : ValueAnimator {
            mValueAnimator = getValueAnimator().apply {
                duration = mDuration
                interpolator = mInterpolator
                addUpdateListener { animation ->
                    if (this@BuildValueAnimator::progressListener.isInitialized) {
                        progressListener.progress(animation.animatedValue as Float)
                    }
                }
            }
            return mValueAnimator
        }

        /**
         * 开始动画
         */
        fun start() {
            mValueAnimator.start()
        }

        /**
         * 生成ValueAnimator
         */
        private fun getValueAnimator() : ValueAnimator {
            return when (mType) {
                Type.INT -> {
                    ValueAnimator.ofInt(*mIntValues)
                }

                Type.FLOAT -> {
                    ValueAnimator.ofFloat(*mFloatValues)
                }

                Type.OBJECT -> {
                    if (this::mEvaluator.isInitialized) {
                        ValueAnimator.ofObject(mEvaluator, *mObjectValues)
                    } else {
                        throw RuntimeException("mEvaluator must be initialized")
                    }
                }

                Type.PROPERTYVALUESHOLDER -> {
                    ValueAnimator.ofPropertyValuesHolder(*mPropertyValues)
                }

                Type.ARGB -> {
                    ValueAnimator.ofArgb(*mIntValues)
                }
            }
        }
    }

    /**
     * 平移动画
     */
    fun translateAnimation(
        type : Translate,
        start : Float,
        end : Float,
        repeatMode : Int = Animation.REVERSE,
        duration : Long = 1000
    ) : TranslateAnimation {
        return when (type) {
            Translate.H -> {
                translateAnimation(start, end, 0f, 0f, repeatMode, duration)
            }

            Translate.V -> {
                translateAnimation(0f, 0f, start, end, repeatMode, duration)
            }
        }
    }

    /**
     * 平移动画
     */
    fun translateAnimation(
        startX : Float,
        endX : Float,
        startY : Float,
        endY : Float,
        repeatMode : Int = Animation.REVERSE,
        duration : Long = 1000
    ) : TranslateAnimation {
        return TranslateAnimation(
            Animation.RELATIVE_TO_SELF, // RELATIVE_TO_SELF 表示操作自身
            startX,                     // startX表示开始的X轴位置
            Animation.RELATIVE_TO_SELF,
            endX,                       // endX表示结束的X轴位置
            Animation.RELATIVE_TO_SELF,
            startY,                     // startY表示开始的Y轴位置
            Animation.RELATIVE_TO_SELF,
            endY                        // endY表示结束的Y轴位置
        ).apply {
            this.repeatMode = repeatMode
            this.duration = duration
        }
    }
}