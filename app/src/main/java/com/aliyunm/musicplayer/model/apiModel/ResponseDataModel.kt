package com.aliyunm.musicplayer.model.apiModel

import com.aliyunm.musicplayer.model.BaseModel

data class ResponseDataModel<T>(
    var responseData : T
) : BaseModel() {
    data class Data<T>(
        var recordCount : Int = 0,
        var records : ArrayList<T> = arrayListOf()
    ) {

    }
}