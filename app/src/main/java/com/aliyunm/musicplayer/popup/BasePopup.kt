package com.aliyunm.musicplayer.popup

import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding
import com.aliyunm.common.utils.AnimationUtils

abstract class BasePopup<VB : ViewBinding>(activity: ComponentActivity) : PopupWindow() {
    var viewBinding: VB
    private var mContext: Context
    private var mWindow: Window
    private var mActivity: ComponentActivity = activity
    private lateinit var view: View
    private var isDark : Boolean = true

    init {
        isClippingEnabled = false
        isOutsideTouchable = true
        isTouchable = true
        isFocusable = true
        mContext = activity.baseContext
        mWindow = activity.window
        viewBinding = getBinding()
        contentView = getView()
        initData()
        initView()
    }

    open fun show(isDark : Boolean = true) {
        this.show(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, isDark)
    }

    open fun show(w : Int, h : Int, isDark : Boolean = true) {
        width = w
        height = h
        this.isDark = isDark
        if (isDark) {
            AnimationUtils.Build()
                .setStart(1f)
                .setEnd(0.5f)
                .setDuration(200)
                .setProgressListener(object : AnimationUtils.ProgressListener {
                    override fun progress(progress: Float) {
                        darkenBackground(progress)
                    }
                })
                .startAnimator()
        }
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

    private fun getView(): View {
        if (!this::view.isInitialized) {
            view = viewBinding.root
        }
        return view
    }

    override fun dismiss() {
        super.dismiss()
        if (isDark) {
            AnimationUtils.Build()
                .setStart(0.5f)
                .setEnd(1f)
                .setDuration(200)
                .setProgressListener(object : AnimationUtils.ProgressListener {
                    override fun progress(progress: Float) {
                        darkenBackground(progress)
                    }
                })
                .startAnimator()
        }
    }

    private fun darkenBackground(bgcolor: Float) {
        val lp = mWindow.attributes
        lp.alpha = bgcolor
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        mWindow.attributes = lp
    }
}