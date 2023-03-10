package com.aliyunm.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.aliyunm.common.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform

object GlideUtils {

    const val CenterCrop : Int = 0
    const val CenterInside : Int = 1
    const val CircleCrop : Int = 2
    const val RoundedCorners : Int = 3
    const val FitCenter : Int = 4

    fun setImage(context: Context, path: Any, view: ImageView, type: Int = 0, radian: Int = 4) {
        val options: RequestOptions = when(type) {
            CenterCrop -> {
                bitmapTransform(CenterCrop())
            }
            CenterInside -> {
                bitmapTransform(CenterInside())
            }
            CircleCrop -> {
                bitmapTransform(CircleCrop())
            }
            RoundedCorners -> {
                bitmapTransform(RoundedCorners(radian))
            }
            FitCenter -> {
                bitmapTransform(FitCenter())
            }
            else -> {
                bitmapTransform(CenterCrop())
            }
        }
        Glide.with(context)
            .load(path)
            .apply(options)
            .placeholder(R.drawable.ic_image_default_1)
            .into(view)
    }

    fun setBlurTransformation(context : Context, bitmap : Bitmap, view : ImageView) {
        Glide.with(context)
            .load(bitmap)
            .placeholder(R.drawable.ic_image_default_1)
            .into(view)
    }
}