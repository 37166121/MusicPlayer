package com.aliyunm.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.aliyunm.common.utils.BitmapUtils
import com.aliyunm.common.utils.GlideUtils
import com.aliyunm.common.utils.GsonUtils
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.reflect.Type

/// json

/**
 * 任意类型转json字符串
 */
fun Any.toJson(): String {
    return GsonUtils.toJson(this)
}

/**
 * json转简单对象
 * @sample fromJson<Object>()
 */
inline fun <reified T> Any.fromJson(): T {
    return fromJson(this, T::class.java)
}

fun<T> fromJson(json: Any, clazz: Class<T>): T {
    return GsonUtils.fromJson(json.toJson(), clazz)
}

/**
 * json转复杂对象
 * @param typeToken
 * @sample fromJson(object : TypeToken<String>(){})
 */
inline fun <reified T> Any.fromJson(typeToken: TypeToken<T>): T {
    return GsonUtils.fromJson(this, typeToken)
}

fun <T> fromJson(json: Any, type: Type): T {
    return GsonUtils.fromJson(json, type)
}

/// Activity

inline fun <reified T : Activity> Context.startActivity() {
    startActivity<T>(null)
}

inline fun <reified T : Activity> Context.startActivity(bundle: Bundle?, vararg flags: Int) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    flags.forEach {
        intent.addFlags(it)
    }
    startActivity(intent, bundle)
}

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int) {
    startActivityForResult<T>(requestCode, null)
}

inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int,
    bundle: Bundle?,
    vararg flags: Int
) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    flags.forEach {
        intent.addFlags(it)
    }
    startActivityForResult(intent, requestCode, bundle)
}

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
 * [EditText]防抖
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