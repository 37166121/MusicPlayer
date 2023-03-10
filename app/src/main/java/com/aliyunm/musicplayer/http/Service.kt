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
            val s = API.getImage(path)
            val bitmap : Bitmap = BitmapFactory.decodeByteArray(s.bytes(), 0, s.bytes().size)
            callback(bitmap)
        }
    }
}