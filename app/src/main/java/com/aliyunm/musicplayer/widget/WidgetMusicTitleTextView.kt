package com.aliyunm.musicplayer.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class WidgetMusicTitleTextView : AppCompatTextView, BaseView {

    private val mContext: Context
    private val mAttrs: AttributeSet?
    private val mDefStyleAttr: Int

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        mAttrs = attrs
        mDefStyleAttr = defStyleAttr
        init()
    }

    override fun init() {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}