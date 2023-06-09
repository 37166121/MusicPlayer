package com.aliyunm.common.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import com.aliyunm.common.utils.ScreenUtils.getScreenHeight
import com.aliyunm.common.utils.ScreenUtils.getScreenWidth
import com.google.android.renderscript.Toolkit
import java.math.BigInteger

object BitmapUtils {
    /**
     * @param drawable xml文件
     * @return
     */
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        val bitmap: Bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 等比缩放图片
     */
    fun zoomImg(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        // 获得图片的宽高
        val width = bm.width
        val height = bm.height
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
    }

    fun createImage(bitmap: Bitmap, activity: Activity): Bitmap {
        // 取得当前屏幕的长宽
        val screenWidth: Int = getScreenHeight(activity)
        val screenHeight: Int = getScreenWidth(activity)
        return zoomImg(bitmap, screenWidth, screenHeight)
    }

    // 图片缩放比例(即模糊度)
    private const val BITMAP_SCALE = 0.4f

    fun blur(image: Bitmap, blurRadius: Float): Bitmap {
        var radius : Int = blurRadius.toInt()
        // radius不在这个范围中时
        if(radius !in 1..25) {
            LogUtils.i("The radius should be between 1 and 25. $radius provided.")
        }
        if (radius < 1) {
            radius = 1
        }
        if (radius > 25) {
            radius = 25
        }
        return Toolkit.blur(image, radius = radius)
    }

    /**
     * @param view          需要模糊的View
     * @param blurRadius    模糊程度
     * @return              模糊处理后的Bitmap
     */
    fun blur(image: Bitmap, view : View, blurRadius: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            view.setRenderEffect(RenderEffect.createBlurEffect(blurRadius, blurRadius, Shader.TileMode.REPEAT))
        } else {
            blur(image, blurRadius)
        }
    }

    /**
     * 裁剪圆形bitmap
     * @param bitmap Bitmap
     * @param asSmall Boolean 尽可能小
     * @return Bitmap
     */
    fun getCircleBitmap(bitmap : Bitmap, asSmall : Boolean = false): Bitmap {
        val paint = Paint()
        paint.isAntiAlias = true
        //获取资源图片
        val bitmap: Bitmap = bitmap.copy(if (asSmall) Bitmap.Config.ARGB_4444 else Bitmap.Config.ARGB_8888, true)
        //创建空位图
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, if (asSmall) Bitmap.Config.ARGB_4444 else Bitmap.Config.ARGB_8888)
        //创建画板
        val canvas = Canvas(output)
        //绘制整个画板为透明
        canvas.drawColor(Color.TRANSPARENT)
        paint.color = Color.WHITE
        //绘制圆形图片
        //取view宽高中的小值 尽量保证图片内容的显示
        val minValue = Math.min(bitmap.width, bitmap.height)
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
}