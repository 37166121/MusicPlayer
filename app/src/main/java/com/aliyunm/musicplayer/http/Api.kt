package com.aliyunm.musicplayer.http

import com.aliyunm.musicplayer.http.Path.REQUEST
import com.aliyunm.musicplayer.model.apiModel.RequestModel
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Streaming
import retrofit2.http.Url

interface Api {
    @GET
    @Streaming
    suspend fun getImage(@Url path : String) : ResponseBody

    @POST(REQUEST)
    suspend fun request(@Body request : RequestModel) : ResponseBody
}