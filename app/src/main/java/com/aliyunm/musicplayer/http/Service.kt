package com.aliyunm.musicplayer.http

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.aliyunm.musicplayer.http.Config.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Service {
    fun getImage(path : String, callback : (Bitmap) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val responseBody = API.getImage(path)
            val bytes = responseBody.bytes()
            val bitmap : Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            callback(bitmap)
        }
    }
}