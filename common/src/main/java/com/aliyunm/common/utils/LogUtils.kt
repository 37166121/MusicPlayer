package com.aliyunm.common.utils

import android.util.Log
import com.aliyunm.common.BuildConfig
import com.aliyunm.common.exception.GlobalExceptionManager

object LogUtils {

    private var isDebug: Boolean = BuildConfig.DEBUG

    fun initialize(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    fun i(vararg msg : String, tag : String = "info") {
        if (isDebug) {
            msg.forEachIndexed { index, s ->
                Log.i("$tag[$index]:", s)
            }
        }
    }

    fun d(vararg msg : String, tag : String = "debug") {
        if (isDebug) {
            msg.forEachIndexed { index, s ->
                Log.d("$tag[$index]:", s)
            }
        }
    }

    fun w(vararg msg : String, tag : String = "warn") {
        if (isDebug) {
            msg.forEachIndexed { index, s ->
                Log.w("$tag[$index]:", s)
            }
        }
    }

    fun e(vararg msg : String, tag : String = "error") {
        if (isDebug) {
            msg.forEachIndexed { index, s ->
                Log.e("$tag[$index]:", s)
            }
        } else {
            log2file()
        }
    }

    fun e(e: Throwable) {
        GlobalExceptionManager.handleException(e)
    }

    /**
     * 日志输出到文件
     */
    fun log2file() {

    }
}