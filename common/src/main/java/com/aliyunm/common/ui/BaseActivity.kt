package com.aliyunm.common.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.aliyunm.common.CommonApplication

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

    /**
     * 全屏
     */
    fun fullScreen(window : Window) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            // 内容延申到状态栏和导航栏
            window.apply {
                setDecorFitsSystemWindows(false)
                statusBarColor = Color.TRANSPARENT
                navigationBarColor = Color.TRANSPARENT
            }
        } else {
            // 全屏显示，隐藏状态栏和导航栏，拉出状态栏和导航栏显示一会儿后消失。
            // window.decorView.systemUiVisibility = (
            //         View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //         or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //         or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            //         or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            //         or View.SYSTEM_UI_FLAG_FULLSCREEN
            //         or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}