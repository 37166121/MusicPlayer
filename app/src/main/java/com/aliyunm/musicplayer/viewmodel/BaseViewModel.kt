package com.aliyunm.musicplayer.viewmodel

import androidx.lifecycle.ViewModel
import com.aliyunm.musicplayer.room.ApplicationDatabase

open class BaseViewModel : ViewModel() {
    lateinit var db : ApplicationDatabase
}