package com.aliyunm.common.utils

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast

object ToastUtils {

    private var mToast: Toast? = null

    fun show(context : Context, msg : String) {
        cancel()
        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        setView(context, msg)
        mToast?.show()
    }

    fun setView(context : Context, msg : String) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            // Snackbar
        } else {
            // API 30之前适用
            mToast?.view = View(context)
        }
    }

    fun cancel() {
        mToast?.cancel()
    }
}