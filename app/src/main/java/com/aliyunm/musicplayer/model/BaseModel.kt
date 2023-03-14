package com.aliyunm.musicplayer.model

import com.aliyunm.common.utils.GsonUtils

abstract class BaseModel {
    fun toJson() : String {
        return GsonUtils.toJson(this)
    }
}