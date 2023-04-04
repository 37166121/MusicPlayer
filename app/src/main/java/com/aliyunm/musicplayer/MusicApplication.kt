package com.aliyunm.musicplayer

import androidx.room.Room
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.SharedPreferencesUtils
import com.aliyunm.musicplayer.room.ApplicationDatabase
import com.aliyunm.musicplayer.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicApplication : CommonApplication() {

    companion object {
        init {
            System.loadLibrary("musicplayer")
            System.loadLibrary("music-helper")
        }
    }

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtils.setSharedPreferences(getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE))
        CoroutineScope(Dispatchers.IO).launch {
            getViewModel(BaseViewModel::class.java).apply {
                db = Room.databaseBuilder(
                    applicationContext,
                    ApplicationDatabase::class.java, "music_database"
                ).build()
            }
        }
    }
}