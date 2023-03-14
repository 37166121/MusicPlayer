package com.aliyunm.musicplayer.http

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.aliyunm.common.utils.GsonUtils
import com.aliyunm.musicplayer.http.Config.API
import com.aliyunm.musicplayer.http.Config.NET_EASE_API
import com.aliyunm.musicplayer.http.Path.SEARCH_SONG
import com.aliyunm.musicplayer.model.MusicModel
import com.aliyunm.musicplayer.model.apiModel.RequestModel
import com.aliyunm.musicplayer.model.apiModel.ResponseBodyModel
import com.aliyunm.musicplayer.model.apiModel.ResponseDataModel
import com.aliyunm.musicplayer.model.apiModel.SearchMusicModel
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

object Service {

    private var mPageSize = 20
    private var mPageNum = 0

    suspend fun response() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun getImage(path : String): Bitmap {
        return CoroutineScope(Dispatchers.IO).async {
            val responseBody = API.getImage(path)
            val bytes = responseBody.bytes()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }.await()
    }

    suspend fun searchMusic(keyword : String, pageSize : Int = mPageSize, pageNum : Int = mPageNum): ResponseDataModel.Data<MusicModel> {
        return CoroutineScope(Dispatchers.IO).async {
            val request : RequestModel = RequestModel(bizContentJson = SearchMusicModel(keyword, pageSize.toString(), pageNum.toString()).toJson(), url = SEARCH_SONG)
            val responseBody = NET_EASE_API.request(request)
            val s = GsonUtils.fromJson(responseBody.string(), object : TypeToken<ResponseBodyModel<ResponseDataModel<ResponseDataModel.Data<MusicModel>>>>(){})
            s.data.responseData
        }.await()
    }

    suspend fun searchSong() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun searchPlaylist() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun searchAlbum() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun searchPlaylistByTag() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun searchSongByArtist() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun playlistTag() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun searchSongByAlbum() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun searchArtists() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun searchSongByArtistSong() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun searchHot() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun playlistSubed() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun playlistSong() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun playlistStar() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun playlistSongLike() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun playlistCreated() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun playlistDetail() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun playlistSub() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun playlistUnsub() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun topList() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun songList() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun songLyric() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun songPlayUrl() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun songTextPlay() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun songDetail() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun songTextDownload() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun songDownloadUrl() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun artistSubed() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun artistsList() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun artistsHotSong() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun albumSong() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun recommendPlaylist() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun recommendSongList() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

    suspend fun login() {
        CoroutineScope(Dispatchers.IO).async {

        }.await()
    }

}