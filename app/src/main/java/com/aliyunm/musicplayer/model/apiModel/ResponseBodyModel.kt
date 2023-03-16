package com.aliyunm.musicplayer.model.apiModel

import com.aliyunm.musicplayer.model.BaseModel

data class ResponseBodyModel<T>(
    val code : Int,
    val data : T?,
    val debugInfo : String = "",
    val failData : String = "",
    val message : String = "",
) : BaseModel() {

}