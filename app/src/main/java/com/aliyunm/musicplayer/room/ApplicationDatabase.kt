package com.aliyunm.musicplayer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliyunm.musicplayer.model.MusicModel
import com.aliyunm.musicplayer.room.dao.MusicDao

@Database(version = 1, entities = [MusicModel::class])
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun getMusicDao(): MusicDao
}