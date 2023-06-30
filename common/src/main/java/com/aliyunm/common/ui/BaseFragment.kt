package com.aliyunm.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.aliyunm.common.CommonApplication

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    lateinit var viewBinding: VB
    lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = setBinding()
        viewModel = getViewModel(setViewModel())
        initData(savedInstanceState)
        initView()
        return viewBinding.root
    }

    /**
     * 请求权限
     */
    fun requestPermission() {

    }

    abstract fun setBinding(): VB

    abstract fun setViewModel() : Class<VM>

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initView()

    fun<T : ViewModel> getViewModel(modelClass: Class<T>) : T {
        return CommonApplication.getApplication().getViewModel(modelClass)
    }
}