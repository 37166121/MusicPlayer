package com.aliyunm.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.ScreenUtils.fullScreen

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    lateinit var viewBinding: VB
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = setBinding()
        viewModel = getViewModel(setViewModel())
        setContentView(viewBinding.root)
        initData(savedInstanceState)
        initView()
        fullScreen(window)
    }

    abstract fun setBinding(): VB

    abstract fun setViewModel() : Class<VM>

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initView()

    fun<T : ViewModel> getViewModel(modelClass: Class<T>) : T {
        return CommonApplication.getApplication().getViewModel(modelClass)
    }
}