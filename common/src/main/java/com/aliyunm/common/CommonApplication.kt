package com.aliyunm.common

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.aliyunm.common.exception.GlobalExceptionManager
import com.aliyunm.common.utils.LogUtils

abstract class CommonApplication : Application(), ViewModelStoreOwner {

    private var commonViewModel: CommonViewModel
    override lateinit var viewModelStore: ViewModelStore

    companion object {
        private lateinit var mApplication: CommonApplication

        fun getApplication(): CommonApplication {
            return mApplication
        }
    }

    init {
        mApplication = this
        viewModelStore = ViewModelStore()
        commonViewModel = getViewModel(CommonViewModel::class.java)
        GlobalExceptionManager
    }

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(getApplication()).get(modelClass)
    }

    fun <T : ViewModel> getViewModel(key: String, modelClass: Class<T>): T {
        return ViewModelProvider(getApplication()).get(key, modelClass)
    }

    override fun onCreate() {
        super.onCreate()
        // 拦截所有异常
        Handler(mainLooper).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    LogUtils.e(e)
                }
            }
        }
    }
}