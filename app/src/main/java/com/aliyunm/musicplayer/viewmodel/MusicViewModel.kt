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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MusicViewModel : BaseViewModel() {

    val historyPosition: ArrayList<Int> = arrayListOf()

    /**
     * 通知页面更改UI
     */
    var position: MutableLiveData<Int> = MutableLiveData()

    var oldPosition = 0

    var nowPosition: Int by Delegates.observable(-1) { property, oldValue, newValue ->
        if (!isFirst) {
            if (musicItems.isNotEmpty()) {
                if (oldValue >= 0) {
                    musicItems[oldValue].isPlaying = false
                }
                musicItems[newValue].isPlaying = true
                player.seekTo(newValue, 0)
                player.play()
                isPlaying.value = true
                position.value = newValue
                oldPosition = newValue
            }
            if (pointer == 0 && oldValue != newValue) {
                historyPosition.add(0, newValue)
            }
            if (pointer == historyPosition.size && oldValue != newValue) {
                pointer++
                historyPosition.add(newValue)
            }
        }
    }

    fun btn() {
        // nowPosition = nowPosition
        isPlaying.value = !isPlaying.value!!
    }

    fun scroll(newValue: Int) {
        if (nowPosition != newValue) {
            nowPosition = newValue
            isPlaying.value = true
        }
    }

    var isFirst = true

    /**
     * 点击控制按钮更改播放状态 或 更改歌单改变控制按钮状态
     */
    var isPlaying: MutableLiveData<Boolean> = MutableLiveData(false)

    /**
     * 播放列表
     */
    val musicItems : LiveArrayList<MusicModel> = LiveArrayList(arrayListOf(
        MusicModel(path = "http://192.168.1.2:8800/Man On The Moon.mp4", name = "Hello", singer = "artistName", coverPath = "http://p1.music.126.net/AiGAvupmUbL-hNQfsfSfeQ==/109951168021305745.jpg")
    ))

    val sessionIdListener: MutableLiveData<Int> = MutableLiveData()

    /**
     * 播放器
     */
    var player: ExoPlayer = ExoPlayer.Builder(CommonApplication.getApplication()).build().apply {
        musicItems.forEach {
            addMediaItem(it.mediaItem)
        }
        repeatMode = Player.REPEAT_MODE_OFF
        addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    Player.STATE_ENDED -> {
                        playerNext(true)
                    }

                    Player.STATE_READY -> {
                        sessionIdListener.value = audioSessionId
                    }
                }
            }

            override fun onPositionDiscontinuity(oldPosition: Player.PositionInfo, newPosition: Player.PositionInfo, reason: Int) {
                if (reason == 0) {
                    playerNext(true)
                }
            }
        })
        addAnalyticsListener(EventLogger())
    }

    /**
     * 控制媒体是否循环以及如何循环
     */
    var repeatMode: MutableLiveData<Int> = MutableLiveData(Player.REPEAT_MODE_OFF)
    var repeatCount: Int by Delegates.observable(repeatMode.value ?: Player.REPEAT_MODE_OFF) { property, oldValue, newValue ->
        repeatMode.value = newValue % (Player.REPEAT_MODE_ALL + 1)
    }

    /**
     * 调整播放速度和音频音调
     */
    var playbackParameters: PlaybackParameters = PlaybackParameters.DEFAULT

    /**
     * 歌单
     */
    lateinit var musicListPopup: MusicListPopup

    var isRecordAudio: Boolean = false

    /**
     * 播放界面
     */
    lateinit var playerPopup: PlayerPopup

    /**
     * 指针 记录历史播放记录位置
     */
    var pointer = 0

    /**
     * 上一首
     */
    fun previous() {
        pointer--
        if (pointer < 0) {
            pointer = 0
        }
        player.pause()
        nextPosition(false)
    }

    /**
     * 下一首
     */
    fun playerNext(isAuto: Boolean) {
        player.pause()
        nextPosition(isAuto)
    }

    /**
     * 计算下一首歌position
     */
    private fun nextPosition(isAuto: Boolean, random: Boolean = false) {
        if (isAuto) {
            if (pointer != historyPosition.size) {
                nowPosition = historyPosition[pointer++]
            }
            nowPosition = when (repeatMode.value) {
                Player.REPEAT_MODE_OFF -> {
                    // 随机循环
                    (Math.random() * musicItems.size).toInt()
                }
                Player.REPEAT_MODE_ONE -> {
                    // 单曲循环
                    if (random) {
                        // 单曲循环 手动下一首时切歌
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
            if (pointer == 0 || pointer == historyPosition.size) {
                nextPosition(true, true)
            } else {
                nowPosition = historyPosition[pointer - 1]
            }
        }
    }

    val progressListener : MutableLiveData<Int> = MutableLiveData()

    fun getProgress() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                CoroutineScope(Dispatchers.Main).launch {
                    progressListener.value = player.currentPosition.toInt()
                }
                delay(1000)
            }
        }
    }
}