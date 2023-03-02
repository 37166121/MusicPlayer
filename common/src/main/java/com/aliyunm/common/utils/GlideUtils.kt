package com.aliyunm.common.utils

import android.content.Context
import android.widget.ImageView
import com.aliyunm.common.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object GlideUtils {
    fun setImage(context : Context, path : Any, view : ImageView, type : Int = 0, radian : Int = 4) {
        val options: RequestOptions = when(type) {
            0 -> {
                RequestOptions.bitmapTransform(CenterCrop())
            }
            1 -> {
                RequestOptions.bitmapTransform(CircleCrop())
            }
            2 -> {
                RequestOptions.bitmapTransform(RoundedCorners(radian))
            }
            else -> {
                RequestOptions.bitmapTransform(CenterCrop())
            }
        }
        Glide.with(context)
            .load(path)
            .apply(options)
            .placeholder(R.drawable.ic_image_default_1)
            .into(view)
    }
}