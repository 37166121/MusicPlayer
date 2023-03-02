package com.aliyunm.musicplayer.popup

import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding

abstract class BasePopup<VB : ViewBinding> : PopupWindow {
    var viewBinding: VB
    private var mContext: Context
    private var mWindow: Window
    private var mActivity: ComponentActivity
    private lateinit var view: View

    constructor(activity: ComponentActivity) : super() {
        mContext = activity.baseContext
        mWindow = activity.window
        mActivity = activity
        viewBinding = getBinding()
        contentView = getView()
        initData()
        initView()
    }

    open fun show() {
        this.show(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    open fun show(w : Int, h : Int) {
        isOutsideTouchable = true
        isTouchable = true
        isFocusable = true
        width = w
        height = h
        darkenBackground(0.5f)
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun getBinding() : VB

    fun getContext(): Context {
        return mContext
    }

    fun getActivity(): ComponentActivity {
        return mActivity
    }

    fun getActivityWindow(): Window {
        return mWindow
    }

    fun getPopupWindow() {

    }

    fun getView(): View {
        if (!this::view.isInitialized) {
            view = viewBinding.root
        }
        return view
    }

    override fun dismiss() {
        super.dismiss()
        darkenBackground(1f)
    }

    private fun darkenBackground(bgcolor: Float) {
        val lp = mWindow.attributes
        lp.alpha = bgcolor
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        mWindow.attributes = lp
    }
}