package com.aliyunm.musicplayer.model.apiModel

import com.aliyunm.musicplayer.model.BaseModel

data class SearchMusicModel(
    /**
     * 搜索内容
     */
    val keyword : String = "",
    /**
     * 每页数据
     */
    val limit : String = "5",
    /**
     * 页码
     */
    val offset : String = "0"
) : BaseModel() {}