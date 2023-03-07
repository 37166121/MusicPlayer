package com.aliyunm.musicplayer.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aliyunm.musicplayer.model.MusicModel

@Dao
interface MusicDao {
    @Query("SELECT * FROM music")
    fun getAllMusic() : Array<MusicModel>

    @Query("SELECT * FROM music WHERE id = :id")
    fun getMusic(id : Long) : MusicModel

    @Query("SELECT * FROM music WHERE name LIKE :name OR composing LIKE :name OR singer LIKE :name")
    fun getMusicByParams(name : String) : Array<MusicModel>

    @Insert
    fun addMusic(vararg musicModel: MusicModel)

    @Update
    fun updateMusic(vararg musicModel: MusicModel)

    @Delete
    fun removeMusic(vararg musicModel: MusicModel)
}