package com.aliyunm.common.exception

import android.util.MalformedJsonException
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.LogUtils
import com.aliyunm.common.utils.ToastUtils
import java.net.UnknownHostException

/**
 * 全局异常管理器
 */
object GlobalExceptionManager : Thread.UncaughtExceptionHandler {
    private const val TAG = "GlobalExceptionManager"

    private val mDefaultCaughtExceptionHandler: Thread.UncaughtExceptionHandler =
        Thread.getDefaultUncaughtExceptionHandler()!!

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        if (!handleException(e)) {
            // 如果没有处理则让系统默认的异常处理器来处理
            mDefaultCaughtExceptionHandler.uncaughtException(t, e)
        } else {
            e.printStackTrace()
        }
    }

    fun handleException(ex: Throwable): Boolean {
        val msg = when (ex) {
            is NullPointerException -> {
                "空指针"
            }

            is UnknownHostException -> {
                "连接服务器失败，请检查网络"
            }

            is MalformedJsonException -> {
                "解析数据失败"
            }

            else -> ex.localizedMessage ?: return false
        }
        LogUtils.e(ex.stackTraceToString(), msg, tag = TAG)
        ToastUtils.show(CommonApplication.getApplication(), msg)
        return true
    }
}