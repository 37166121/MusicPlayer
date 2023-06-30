package com.aliyunm.common.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.ScreenUtils.fullScreen
import com.blankj.utilcode.util.KeyboardUtils

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
     * 请求权限
     */
    fun requestPermission() {

    }

    /**
     * 点击空白处隐藏软键盘
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v : View? = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                KeyboardUtils.hideSoftInput(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private fun isShouldHideKeyboard(v : View?, event : MotionEvent) : Boolean {
        if ((v is EditText)) {
            val l = IntArray(2) {
                return@IntArray 0
            }
            v.getLocationOnScreen(l)
            val left : Int = l[0]
            val top : Int = l[1]
            val bottom : Int = top + v.getHeight()
            val right : Int = left + v.getWidth()
            return !(event.rawX > left && event.rawX < right
                    && event.rawY > top && event.rawY < bottom)
        }
        return false
    }
}