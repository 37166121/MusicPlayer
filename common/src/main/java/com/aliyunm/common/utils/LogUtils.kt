package com.aliyunm.common.utils

import android.util.Log
import com.aliyunm.common.exception.GlobalExceptionManager

object LogUtils {

    private var isDebug: Boolean = true

    fun initialize(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    fun i(msg : String, tag : String = "info") {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    fun d(msg : String, tag : String = "debug") {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    fun w(msg : String, tag : String = "warn") {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    fun e(msg : String, tag : String = "error") {
        Log.e(tag, msg)
        if (!isDebug) {
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