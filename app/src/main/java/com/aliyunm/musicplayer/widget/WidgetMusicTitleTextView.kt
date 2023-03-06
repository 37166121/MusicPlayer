package com.aliyunm.musicplayer.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.aliyunm.musicplayer.model.MusicModel

class WidgetMusicTitleTextView : AppCompatTextView, BaseView {

    private val mContext: Context
    private val mAttrs: AttributeSet?
    private val mDefStyleAttr: Int

    private var title : String = ""
    private lateinit var mMusicModel: MusicModel

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

    fun setTitle() {

    }

    fun setMusicItem(musicModel: MusicModel) {
        mMusicModel = musicModel
    }

    fun setMusicName(): String {
        return mMusicModel.name
    }

    fun setSinger(): String {
        val singer : StringBuffer = StringBuffer()
        mMusicModel.singer.split(",").forEach {
            singer.append(it).append("/")
        }
        return singer.substring(0, singer.length - 1)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}