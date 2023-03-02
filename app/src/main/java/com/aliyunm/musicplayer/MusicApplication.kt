package com.aliyunm.musicplayer

import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.SharedPreferencesUtil

class MusicApplication : CommonApplication() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtil.setSharedPreferences(getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE))
    }
}