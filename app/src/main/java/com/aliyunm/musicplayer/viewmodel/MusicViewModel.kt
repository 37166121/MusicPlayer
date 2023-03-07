package com.aliyunm.musicplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.aliyunm.common.CommonApplication
import com.aliyunm.musicplayer.LiveArrayList
import com.aliyunm.musicplayer.http.Path.BASEURL
import com.aliyunm.musicplayer.model.MusicModel
import com.aliyunm.musicplayer.popup.MusicListPopup
import com.aliyunm.musicplayer.popup.PlayerPopup
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.EventLogger
import kotlin.properties.Delegates

class MusicViewModel : BaseViewModel() {

    val historyPosition : ArrayList<Int> = arrayListOf()

    var oldPosition = 0

    var nowPosition : Int by Delegates.observable(-1) { property, oldValue, newValue ->
        if (!isFirst) {
            if (musicItems.isNotEmpty()) {
                if (oldValue >= 0) {
                    musicItems[oldValue].isPlaying = false
                }
                musicItems[newValue].isPlaying = true
                player.seekTo(newValue, 0)
                player.play()
            }
            position.value = newValue
            oldPosition = newValue
            if (pointer == historyPosition.size) {
                if (oldValue != newValue) {
                    pointer++
                    historyPosition.add(newValue)
                }
            }
        }
    }

    fun btn() {
        isPlaying.value = !isPlaying.value!!
    }

    fun scroll(newValue : Int) {
        if (nowPosition != newValue) {
            nowPosition = newValue
            isPlaying.value = true
        }
    }

    var isFirst = true

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
        MusicModel(path = "${BASEURL}/Man On The Moon.mp4", name = "Man on the Moon", singer = "Alan Walker,Benjamin Ingrosso", coverPath = "https://p1.music.126.net/_lXJ8hBLVaRNipEQ-J6ULQ==/109951166633315099.jpg"),
        MusicModel(path = "${BASEURL}/Headlights.mp4", name = "Headlights", singer = "Alok,Alan Walker,KIDDO", coverPath = "https://p1.music.126.net/XPDYL0mXgEik54BThDVa9g==/109951167054187749.jpg"),
        MusicModel(path = "${BASEURL}/One Life.mp4", name = "One Life", singer = "Mike Perry", coverPath = "http://p1.music.126.net/6O678esjRmrE2XOFo4FcwQ==/109951164389106666.jpg"),
        MusicModel(path = "${BASEURL}/Cyberpunk 2077.mp3", name = "Cyberpunk 2077", singer = "老兵", coverPath = "http://192.168.1.2:8800/Cyberpunk 2077.png"),
        MusicModel(path = "${BASEURL}/test.mp3", name = "test", singer = "test", coverPath = "http://192.168.1.2:8800/Cyberpunk 2077.png"),
        MusicModel(path = "${BASEURL}/Hello.mp4", name = "Hello", singer = "Barbara Opsomer", coverPath = "https://p2.music.126.net/gJBIEu6O22yD1nf1NE0Xig==/109951167189052612.jpg")
    ))

    val copy_musicItems : LiveArrayList<MusicModel> = musicItems.clone() as LiveArrayList<MusicModel>

    /**
     * 播放器
     */
    var player: ExoPlayer = ExoPlayer.Builder(CommonApplication.getApplication()).build().apply {
        musicItems.forEach {
            addMediaItem(it.mediaItem)
        }
        repeatMode = Player.REPEAT_MODE_OFF
        addListener(object : Player.Listener {
            override fun onPositionDiscontinuity(oldPosition: Player.PositionInfo, newPosition: Player.PositionInfo, reason: Int) {
                if (reason == 0) {
                    playerNext(true)
                }
            }
        })
        addAnalyticsListener(EventLogger())
        prepare()
    }

    /**
     * 控制媒体是否循环以及如何循环
     */
    var repeatMode: Int = Player.REPEAT_MODE_OFF
    var repeatCount: Int = repeatMode

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

    /**
     * 指针 记录历史播放记录位置
     */
    var pointer = 0

    /**
     * 上一首
     */
    fun previous() {
        pointer--
        player.pause()
        nextPosition(false)
    }

    /**
     * 下一首
     */
    fun playerNext(isAuto : Boolean) {
        player.pause()
        nextPosition(isAuto)
    }

    /**
     * 计算下一首歌position
     */
    private fun nextPosition(isAuto : Boolean, random : Boolean = false) {
        if (isAuto) {
            if (pointer != historyPosition.size) {
                nowPosition = historyPosition[pointer]
            }
            nowPosition = when(repeatMode) {
                Player.REPEAT_MODE_OFF -> {
                    // 随机循环
                    (Math.random() * musicItems.size).toInt()
                }
                Player.REPEAT_MODE_ONE -> {
                    // 单曲循环
                    if (random) {
                        // 列表循环 手动下一首时切歌
                        (nowPosition + 1) % musicItems.size
                    } else {
                        nowPosition
                    }
                }
                Player.REPEAT_MODE_ALL -> {
                    // 列表循环
                    (nowPosition + 1) % musicItems.size
                }
                else -> {
                    // 列表循环
                    (nowPosition + 1) % musicItems.size
                }
            }
        } else {
            if (pointer == historyPosition.size) {
                nextPosition(true, true)
            } else {
                nowPosition = historyPosition[pointer]
            }
        }
    }

}