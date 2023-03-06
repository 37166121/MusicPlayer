package com.aliyunm.musicplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aliyunm.musicplayer.LiveArrayList
import com.aliyunm.musicplayer.model.MusicModel
import com.aliyunm.musicplayer.popup.MusicListPopup
import com.aliyunm.musicplayer.popup.PlayerPopup
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerControlView
import kotlin.properties.Delegates

class MusicViewModel : ViewModel {

    var nowPosition : Int by Delegates.observable(-1) { property, oldValue, newValue ->
        if (musicItems.isNotEmpty()) {
            if (oldValue >= 0) {
                musicItems[oldValue].isPlaying = false
            }
            musicItems[newValue].isPlaying = true
            player.seekTo(newValue, 0)
            isPlaying.value = true
        }
        position.value = newValue
    }

    /**
     * 点击控制按钮更改播放状态 或 更改歌单改变控制按钮状态
     */
    var isPlaying : MutableLiveData<Boolean> = MutableLiveData(false)

    /**
     * 通知页面更改UI
     */
    var position : MutableLiveData<Int> = MutableLiveData()

    /**
     * 播放列表
     */
    val musicItems : LiveArrayList<MusicModel> = LiveArrayList(arrayListOf(
        MusicModel(path = "http://192.168.1.2:8800/Man On The Moon.mp4", name = "Man on the Moon", singer = "Alan Walker,Benjamin Ingrosso", coverPath = "https://p1.music.126.net/_lXJ8hBLVaRNipEQ-J6ULQ==/109951166633315099.jpg"),
        MusicModel(path = "http://192.168.1.2:8800/Headlights.mp4", name = "Headlights", singer = "Alok,Alan Walker,KIDDO", coverPath = "https://p1.music.126.net/XPDYL0mXgEik54BThDVa9g==/109951167054187749.jpg"),
        MusicModel(path = "http://192.168.1.2:8800/One Life.mp4", name = "One Life", singer = "Mike Perry", coverPath = "http://p1.music.126.net/6O678esjRmrE2XOFo4FcwQ==/109951164389106666.jpg"),
        MusicModel(path = "http://192.168.1.2:8800/Hello.mp4", name = "Hello", singer = "Barbara Opsomer", coverPath = "https://p2.music.126.net/gJBIEu6O22yD1nf1NE0Xig==/109951167189052612.jpg")
    ))

    val copy_musicItems : LiveArrayList<MusicModel> = musicItems.clone() as LiveArrayList<MusicModel>

    /**
     * 播放器
     */
    lateinit var player: ExoPlayer
    lateinit var playerControlView: PlayerControlView

    /**
     * 控制媒体是否循环以及如何循环
     */
    var repeatMode: Int = Player.REPEAT_MODE_ALL
    var repeatCount: Int = repeatMode

    /**
     * 控制播放列表随机播放
     */
    var shuffleModeEnabled: Boolean = false

    /**
     * 调整播放速度和音频音调
     */
    var playbackParameters: PlaybackParameters = PlaybackParameters.DEFAULT

    /**
     * 歌单
     */
    lateinit var musicListPopup : MusicListPopup

    /**
     * 播放界面
     */
    lateinit var playerPopup : PlayerPopup

    constructor() : super() {

    }
}