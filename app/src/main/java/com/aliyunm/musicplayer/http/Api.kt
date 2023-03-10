package com.aliyunm.musicplayer.http

import android.database.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface Api {
    @GET
    @Streaming
    suspend fun getImage(@Url path : String) : ResponseBody
}