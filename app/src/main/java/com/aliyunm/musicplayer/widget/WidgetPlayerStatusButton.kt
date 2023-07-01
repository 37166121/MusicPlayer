package com.aliyunm.musicplayer.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.aliyunm.musicplayer.R
import com.aliyunm.common.utils.BitmapUtils

class WidgetPlayerStatusButton : ProgressBar, OnClickListener, BaseView {

    private val mContext: Context
    private val mAttrs: AttributeSet?
    private val mDefStyleAttr: Int
    private val mDefStyleRes: Int

    /**
     * 播放状态
     */
    private var isPlaying: Boolean = false
    private var mWidth = 0
    private var mHeight = 0
    private var mStrokeWidth = 15f
    private var b = 100
    private lateinit var pause: Bitmap
    private lateinit var play: Bitmap
    private var clickListener: () -> Unit = {}

    private val bgPaint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        style = Paint.Style.STROKE
        isAntiAlias = true
        isDither = true
    }

    private val paint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        style = Paint.Style.STROKE
        isAntiAlias = true
        isDither = true
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        mContext = context
        mAttrs = attrs
        mDefStyleAttr = defStyleAttr
        mDefStyleRes = defStyleRes
        setOnClickListener(this)
        init()
    }

    override fun init() {
        pause = BitmapUtils.drawableToBitmap(ContextCompat.getDrawable(mContext, R.drawable.ic_pause)!!)
        play = BitmapUtils.drawableToBitmap(ContextCompat.getDrawable(mContext, R.drawable.ic_play)!!)
    }

    private lateinit var mCanvas: Canvas

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCanvas = canvas
        val rect: RectF = getDiameter()
        // 背景圆圈
        canvas.drawArc(rect, 0f, 360f, false, bgPaint.apply {
            strokeWidth = mStrokeWidth
            color = mContext.getColor(R.color.play_button_color_bg)
        })
        // 进度圆圈
        canvas.drawArc(rect, -90f, progress * (360f / max), false, paint.apply {
            strokeWidth = mStrokeWidth
            color = mContext.getColor(R.color.play_button_color)
        })
        drawStatus()
    }

    /**
     * 获取直径 画笔宽度 居中位置
     */
    private fun getDiameter(): RectF {
        // 直径
        b = if (mWidth > mHeight) mHeight else mWidth
        // 画笔宽度
        mStrokeWidth = b / 25f
        // 居中位置
        return RectF(
            getLeftSpace() + mStrokeWidth / 2 + paddingStart,
            getTopSpace() + mStrokeWidth / 2 + paddingTop,
            b - mStrokeWidth / 2 - paddingEnd + getLeftSpace(),
            b - mStrokeWidth / 2 - paddingBottom + getTopSpace()
        )
    }

    private fun getLeftSpace(): Int {
        return mWidth / 2 - b / 2
    }

    private fun getTopSpace(): Int {
        return mHeight / 2 - b / 2
    }

    private fun getLeftSpaceBitmap(bitmap: Bitmap): Int {
        return mWidth / 2 - bitmap.width / 2
    }

    private fun getTopSpaceBitmap(bitmap: Bitmap): Int {
        return mHeight / 2 - bitmap.height / 2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize
        } else {
            mWidth = widthSize
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize
        } else {
            mHeight = heightSize
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onClick(v: View?) {
        clickListener()
        // invalidate()
    }

    /**
     * 设置进度 重绘
     * @param progress Int
     */
    override fun setProgress(progress: Int) {
        super.setProgress(progress)
        invalidate()
    }

    /**
     * 画播放状态
     */
    private fun drawStatus() {
        var bitmap = if (isPlaying) {
            // 播放状态 显示暂停按钮
            pause
        } else {
            // 暂停状态 显示播放按钮
            play
        }
        bitmap = BitmapUtils.zoomImg(bitmap, (b / 2f).toInt(), (b / 2f).toInt())
        mCanvas.drawBitmap(
            bitmap, getLeftSpaceBitmap(bitmap) + 0f,
            getTopSpaceBitmap(bitmap) + 0f, paint
        )
    }

    fun setClickListener(clickListener: () -> Unit) {
        this.clickListener = clickListener
    }

    /**
     * 切换播放状态 重绘
     * @param playing Boolean
     */
    fun switchPlayStatus(playing : Boolean) {
        isPlaying = playing
        invalidate()
    }
}