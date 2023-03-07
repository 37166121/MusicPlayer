package com.aliyunm.common

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

abstract class CommonApplication : Application(), ViewModelStoreOwner {

    private var commonViewModel : CommonViewModel
    private var mViewModelStore: ViewModelStore
    companion object {
        private lateinit var mApplication: CommonApplication

        fun getApplication(): CommonApplication {
            return mApplication
        }
    }

    init {
        mApplication = this
        mViewModelStore = ViewModelStore()
        commonViewModel = getViewModel(CommonViewModel::class.java)
    }

    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }

    fun<T : ViewModel> getViewModel(modelClass: Class<T>) : T {
        return ViewModelProvider(getApplication()).get(modelClass)
    }

    fun<T : ViewModel> getViewModel(key : String, modelClass: Class<T>) : T {
        return ViewModelProvider(getApplication()).get(key, modelClass)
    }

}