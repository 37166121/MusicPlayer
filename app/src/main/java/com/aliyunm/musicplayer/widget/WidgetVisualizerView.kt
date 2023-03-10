package com.aliyunm.musicplayer.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.media.audiofx.Visualizer
import android.util.AttributeSet
import android.view.View
import com.aliyunm.common.utils.BitmapUtils
import com.aliyunm.musicplayer.widget.WidgetVisualizerView.Type.SATELLITE
import com.aliyunm.musicplayer.widget.WidgetVisualizerView.Type.VERTICAL
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
     * 时域数据
     */
    private var model : FloatArray = floatArrayOf()

    /**
     * 根据 [mWireCount] 计算平均值
     */
    private var modelAvg : FloatArray = floatArrayOf()

    /**
     * 歌曲封面
     */
    private lateinit var mMusicCover : Bitmap

    /**
     * 形状类型
     */
    private var mType : Int = SATELLITE

    /**
     * 线条总数
     */
    private var mWireCount : Int = 36

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
     * 时域图形状
     */
    object Type {

        /**
         * 线性
         */
        const val WAVE = 0

        /**
         * 柱状
         */
        const val VERTICAL = 1

        /**
         * 卫星
         */
        const val SATELLITE = 2
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
        if (this::mMusicCover.isInitialized) {
            drawCover(mMusicCover, canvas)
        }

        // val path : Path = Path()
        // path.cubicTo(0f, 0f, 10f, 15f, 20f, 25f)
        // canvas.drawPath(path, Paint().apply {
        //     strokeWidth = 5f
        //     strokeCap = Paint.Cap.ROUND
        //     strokeJoin = Paint.Join.ROUND
        //     style = Paint.Style.STROKE
        // })
        // path.cubicTo(250f, 80f, 180f, 150f, 100f, 50f)
        // canvas.drawPath(path, Paint().apply {
        //     strokeWidth = 5f
        //     strokeCap = Paint.Cap.ROUND
        //     strokeJoin = Paint.Join.ROUND
        //     style = Paint.Style.STROKE
        // })

        // test()

        getCountWidth()
        smooth()
        translate(canvas)
        drawS(canvas)
    }

    private fun translate(canvas: Canvas) {
        when (mType) {
            WAVE        -> {
                canvas.translate(mWidth / 2f, mHeight / 2f)
            }
            VERTICAL    -> {
                canvas.translate(getStartX(), b / 2 + 0f)
            }
            SATELLITE   -> {
                canvas.translate(mWidth / 2f, mHeight / 2f)
            }
        }
    }

    private fun drawS(canvas: Canvas) {
        var x = 0f
        modelAvg.forEachIndexed { index, it ->
            when (mType) {
                WAVE        -> {
                    // 画圆弧
                }
                VERTICAL    -> {
                    // 画柱状
                    canvas.drawLine(x, 0f, x, -it, paint)
                    x += mSpacing * 2
                }
                SATELLITE   -> {
                    // 画卫星柱状
                    val angle = 360 / mWireCount
                    val r = getR() / 3
                    val i = index * angle
                    val x0 = r * cos((PI / 180 * i) - (PI / 2))
                    val x1 = x0 + (r + it / 4) * cos((PI / 180 * i) - (PI / 2))
                    val y0 = r * sin((PI / 180 * i) - (PI / 2))
                    val y1 = y0 + (r + it / 4) * sin((PI / 180 * i) - (PI / 2))
                    canvas.drawLine(x0.toFloat(), y0.toFloat(), x1.toFloat(), y1.toFloat(), paint.apply {
                        color = Color.WHITE
                    })
                }
            }
        }
    }

    fun drawCover(musicCover : Bitmap, canvas: Canvas) {
        val p = Paint().apply {
            isAntiAlias = true //去锯齿
            color = Color.BLACK
            style = Paint.Style.STROKE
        }
        var bitmap = getCircleBitmap(musicCover)
        bitmap = BitmapUtils.zoomImg(bitmap, (b / 3).toInt(), (b / 3).toInt())
        canvas.drawBitmap(bitmap, getLeftSpaceBitmap(bitmap) + 0f,getTopSpaceBitmap(bitmap) + 0f, paint)
    }

    private fun getCircleBitmap(musicCover : Bitmap): Bitmap {
        val paint = Paint()
        paint.isAntiAlias = true
        //获取资源图片
        val bitmap: Bitmap = musicCover.copy(Bitmap.Config.ARGB_8888, true)
        //创建空位图
        val output = Bitmap.createBitmap(musicCover.width, musicCover.height, Bitmap.Config.ARGB_8888)
        //创建画板
        val canvas = Canvas(output)
        //绘制整个画板为透明
        canvas.drawColor(Color.TRANSPARENT)
        paint.color = Color.WHITE
        //绘制圆形图片
        //取view宽高中的小值 尽量保证图片内容的显示
        val minValue = Math.min(musicCover.width, musicCover.height)
        //设置半径
        val mRadius = minValue / 2f
        canvas.drawCircle(mRadius, mRadius, mRadius, paint)
        //设置图形相交模式
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val src = Rect(0, 0, bitmap.width, bitmap.height)
        val dst = Rect(0, 0, output.width, output.height)
        canvas.drawBitmap(bitmap, src, dst, paint)
        return output
    }

    private fun test() {
        modelAvg = FloatArray(mWireCount)
        for (i in 0 until mWireCount) {
            modelAvg[i] = (Math.random() * 600f).toFloat()
        }
    }

    /**
     * 平滑
     */
    private fun smooth() {
        if (modelAvg.isNotEmpty()) {
            val max = modelAvg.sortedDescending()[0]
            val r = getR()
            modelAvg.clone().apply {
                forEachIndexed { index, it ->
                    val x = when {
                        it < max / 20   -> { 6 }
                        it < max / 15   -> { 5 }
                        it < max / 10   -> { 4 }
                        it < max / 7    -> { 3 }
                        it < max / 5    -> { 2 }
                        else            -> { 1 }
                    }
                    modelAvg[index] = it * x / max * r
                }
            }
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
    private fun getR(): Float {
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
    }

    /**
     * 设置形状类型
     */
    fun setType(type : Int) {
        mType = type
    }

    fun setVisualizer(sessionId : Int) {
        setVisualizer(Visualizer(sessionId).apply {
            enabled = false
            captureSize = Visualizer.getCaptureSizeRange()[1]
            setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
                override fun onWaveFormDataCapture(visualizer: Visualizer, waveform: ByteArray, samplingRate: Int) {

                }

                override fun onFftDataCapture(visualizer: Visualizer, fft: ByteArray, samplingRate: Int) {
                    val model = FloatArray(fft.size / 2)
                    // var j = 1
                    // var i = 2
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
        invalidate()
    }

    private fun modelAvg() {
        val size = model.size / mWireCount
        modelAvg = FloatArray(mWireCount)
        for (i in 0 until mWireCount) {
            val s = model.slice(i * size until (i + 1) * size)
            var count = 0f
            s.forEach {
                count += it
            }
            modelAvg[i] = count / size
        }
    }

    /**
     * 是否启用Visualizer
     */
    fun setVisualizerEnabled(enabled : Boolean) {
        mVisualizer.enabled = enabled
    }
}