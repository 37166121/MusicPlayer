package com.aliyunm.musicplayer.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.media.audiofx.Visualizer
import android.util.AttributeSet
import android.view.View
import androidx.palette.graphics.Palette
import com.aliyunm.common.utils.BitmapUtils
import com.aliyunm.common.utils.BitmapUtils.getCircleBitmap
import com.aliyunm.musicplayer.widget.WidgetVisualizerView.Type.SATELLITE
import com.aliyunm.musicplayer.widget.WidgetVisualizerView.Type.WAVE
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

class WidgetVisualizerView : View, BaseView {

    private val mContext: Context
    private val mAttrs: AttributeSet?
    private val mDefStyleAttr: Int
    private val mDefStyleRes: Int

    private lateinit var mVisualizer : Visualizer

    /**
     * 播放状态
     */
    private var isPlaying: Boolean = false

    /**
     * 歌曲封面
     */
    private lateinit var mMusicCover : Bitmap

    /**
     * 形状类型
     */
    private var mType : Int = SATELLITE

    private val multiple = 5

    private var takeCount = 12

    /**
     * 线条总数
     */
    private var mWireCount : Int = takeCount * multiple

    /**
     * 频域数据
     */
    private var model : FloatArray = floatArrayOf()

    /**
     * 根据 [mWireCount] 计算频域数据平均值
     */
    private var modelAvg : FloatArray = FloatArray(mWireCount)

    /**
     * 竖线宽度
     */
    private var mWireWidth : Float = 15f

    /**
     * 竖状图间距
     */
    private var mSpacing : Float = 10f

    /**
     * 卫星图弧度
     */
    private var mRadian : Float = 10f

    /**
     * 连接线宽度
     */
    private var mLinkWidth : Float = 10f

    /**
     * 连接线宽度
     */
    private var mStrokeWidth : Float = 10f

    /**
     * 顶点是否互相连接
     */
    private var mIsLink : Boolean = false

    /**
     * 顶点连线是否平滑
     */
    private var mIsSmooth : Boolean = false

    private var mWidth = 0
    private var mHeight = 0
    private var b = 100
    private var r = -1f

    /**
     * 频域图形状
     */
    object Type {

        /**
         * 线性
         */
        const val WAVE = 0

        /**
         * 卫星
         */
        const val SATELLITE = 1
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        mContext = context
        mAttrs = attrs
        mDefStyleAttr = defStyleAttr
        mDefStyleRes = defStyleRes
        init()
    }

    override fun init() {

    }

    private val paint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        style = Paint.Style.STROKE
        isAntiAlias = true
        isDither = true
        color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        getCountWidth()
        translate(canvas)
        drawS(canvas)
    }

    private fun translate(canvas: Canvas) {
        canvas.translate(mWidth / 2f, mHeight / 2f)
    }

    private fun drawS(canvas: Canvas) {
        var x = 0f
        modelAvg.forEachIndexed { index, it ->
            when (mType) {
                WAVE        -> {
                    // 画圆弧
                }
                SATELLITE   -> {
                    // 画卫星柱状
                    val angle = 360 / mWireCount
                    val r = getR() / 1.8
                    val i = index * angle
                    val s = 180
                    val d = PI / 2
                    val x0 = r * cos((PI / s * i) - d)
                    val x1 = x0 + (it / 2) * cos((PI / s * i) - d)
                    val y0 = r * sin((PI / s * i) - d)
                    val y1 = y0 + (it / 2) * sin((PI / s * i) - d)
                    canvas.drawLine(x0.toFloat(), y0.toFloat(), x1.toFloat(), y1.toFloat(), paint)
                }
            }
        }
    }

    fun drawCover(musicCover : Bitmap, canvas: Canvas) {
        var bitmap = getCircleBitmap(musicCover)
        bitmap = BitmapUtils.zoomImg(bitmap, (b / 2).toInt(), (b / 2).toInt())
        canvas.drawBitmap(bitmap, getLeftSpaceBitmap(bitmap) + 0f,getTopSpaceBitmap(bitmap) + 0f, paint.apply {
            color = Color.BLACK
        })
    }

    private fun test() {
        modelAvg = FloatArray(mWireCount)
        for (i in 0 until mWireCount) {
            modelAvg[i] = (Math.random() * 600f).toFloat()
        }
    }

    /**
     * x开始位置 宽度 - 可用半径
     */
    private fun getStartX(): Float {
        return b / 2 - getR()
    }

    /**
     * 获取半径
     */
    fun getR(): Float {
        return if (r == -1f) {
            val padding = max(max(paddingLeft, paddingRight), max(paddingTop, paddingBottom))
            r = (min(mWidth, mHeight) - padding) / 2f
            r
        } else {
            r
        }
    }

    /**
     * 获取宽度和间距
     * ① 减去内边距 mSize - padding
     * ② 所需宽度 (竖线 + 空隙) * 线条总数 - 空隙  mWireCount * 2 - 1
     * ① / ② = 竖线 + 空隙 宽度
     */
    private fun getCountWidth() {
        (getR() * 2 / (mWireCount * 2 - 1f)).apply {
            mSpacing = this
            mWireWidth = this
            paint.strokeWidth = this
        }
    }

    /**
     * 居中位置
     */
    private fun getDiameter(): RectF {
        // 居中位置
        return RectF(
            getLeftSpace() + paddingStart + 0f,
            getTopSpace() + paddingTop + 0f,
            b - paddingEnd + getLeftSpace() + 0f,
            b - paddingBottom + getTopSpace() + 0f
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

        mWidth = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            widthSize
        }

        mHeight = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            heightSize
        }

        // 直径
        b = min(mWidth, mHeight)

        setMeasuredDimension(mWidth, mHeight)
    }

    /**
     * 设置封面
     */
    fun setMusicCover(musicCover : Bitmap) {
        mMusicCover = musicCover
        getVibrantColor(mMusicCover).apply {
            paint.color = this
        }
        invalidate()
    }

    /**
     * 获取封面主题色
     */
    private fun getVibrantColor(musicCover : Bitmap): Int {
        val palette : Palette = Palette.from(getCircleBitmap(musicCover)).generate()
        return palette.swatches.first().rgb
    }

    /**
     * 设置形状类型
     */
    fun setType(type : Int) {
        mType = type
    }

    /**
     * 获取频域数据
     */
    fun setVisualizer(sessionId : Int) {
        setVisualizer(Visualizer(sessionId).apply {
            enabled = false
            captureSize = Visualizer.getCaptureSizeRange()[1]
            setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
                override fun onWaveFormDataCapture(visualizer: Visualizer, waveform: ByteArray, samplingRate: Int) {

                }

                override fun onFftDataCapture(visualizer: Visualizer, fft: ByteArray, samplingRate: Int) {
                    val model = FloatArray(fft.size / 2)
                    var j = 0
                    var i = 1
                    while (i < (model.size - 1) * 2) {
                        model[j] = hypot(fft[i].toDouble(), fft[i + 1].toDouble()).toFloat()
                        i += 2
                        j++
                        model[j] = abs(model[j])
                    }
                    if (model.sum() > 0) {
                        setModel(model)
                    }
                }
            }, Visualizer.getMaxCaptureRate() / 2, false, true)
        })
    }

    fun setVisualizer(visualizer : Visualizer) {
        var enabled = false
        if (this::mVisualizer.isInitialized) {
            enabled = mVisualizer.enabled
            mVisualizer.enabled = false
        }
        mVisualizer = visualizer
        mVisualizer.enabled = enabled
    }

    /**
     * 设置数据
     */
    fun setModel(model : FloatArray) {
        this.model = model
        modelAvg()
        smooth()
        invalidate()
    }

    private fun modelAvg() {
        modelAvg = FloatArray(mWireCount)
        val size = model.size / takeCount
        for (i in 0 until takeCount) {
            val s = model.slice(i * size until (i + 1) * size)
            modelAvg[i * multiple] = s.sum() / size
        }
    }

    /**
     * 平滑数据
     */
    private fun smooth() {
        if (modelAvg.isNotEmpty()) {
            val max = modelAvg.sortedDescending()[0]
            val r = getR() / 2
            modelAvg[0] = max / 2
            // 放大
            modelAvg.clone().apply {
                forEachIndexed { index, it ->
                    val x = when {
                        it < max / 20   -> { 11 }
                        it < max / 15   -> { 9 }
                        it < max / 10   -> { 7 }
                        it < max / 7    -> { 5 }
                        it < max / 5    -> { 3 }
                        else            -> { 1 }
                    }
                    modelAvg[index] = it * x / max * r
                }
            }
            // 平滑 插值
            for (i in 1 .. takeCount) {
                val start = modelAvg[(i - 1) * multiple]
                val end = if (i == takeCount) {
                    modelAvg[0]
                } else {
                    modelAvg[i * multiple]
                }
                val abs = abs(start - end) / multiple
                for (j in ((i - 1) * multiple)until(i * multiple - 1)) {
                    modelAvg[j + 1] = modelAvg[j] + if (start > end) {
                        -abs
                    } else {
                        +abs
                    }
                }
            }

        }
    }

    /**
     * 是否启用Visualizer
     */
    fun setVisualizerEnabled(enabled : Boolean) {
        mVisualizer.enabled = enabled
    }
}