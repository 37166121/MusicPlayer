package com.aliyunm.common

import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.aliyunm.common.utils.BitmapUtils
import com.aliyunm.common.utils.GlideUtils
import com.aliyunm.common.utils.ViewUtils
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * 设置图片
 */
fun ImageView.setImage(path: Any, type: Int = 0, radian: Int = 4) {
    GlideUtils.setImage(context, path, this, type, radian)
}

/**
 * 高斯模糊
 */
fun ImageView.setBlurTransformation(bitmap: Bitmap, blurRadius : Float = 50f) {
    GlideUtils.setBlurTransformation(context, bitmap, this)
    BitmapUtils.blur(bitmap, this, blurRadius)
}

/**
 * [View] 高斯模糊
 */
fun View.setGaussianBlurTransformation(blurRadius : Float = 25F) {
    // TODO View高斯模糊
}

/**
 * EditText防抖
 */
fun EditText.textChangeFlow(): Flow<Editable> = callbackFlow {
    // 构建输入框监听器
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int ) { }
        // 在文本变化后向流发射数据
        override fun onTextChanged( s: CharSequence?, start: Int, before: Int, count: Int ) {
            s?.let { trySend(it as Editable).isSuccess }
        }
    }
    addTextChangedListener(watcher)
    awaitClose { removeTextChangedListener(watcher) } // 阻塞
}