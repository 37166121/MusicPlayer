package com.aliyunm.musicplayer.model

import com.aliyunm.common.toJson

abstract class BaseModel {
    fun toJson() : String {
        return (this as Any).toJson()
    }
}