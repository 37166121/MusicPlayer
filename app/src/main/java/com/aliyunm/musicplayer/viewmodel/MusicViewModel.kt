package com.aliyunm.musicplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.aliyunm.common.CommonApplication
import com.aliyunm.musicplayer.LiveArrayList
import com.aliyunm.musicplayer.model.MusicModel
import com.aliyunm.musicplayer.popup.MusicListPopup
import com.aliyunm.musicplayer.popup.PlayerPopup
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.EventLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MusicViewModel : BaseViewModel() {

    /**
     * 通知页面更改UI
     */
    var position: MutableLiveData<Int> = MutableLiveData()

    var oldPosition = 0

    var nowPosition: Int by Delegates.observable(-1) { property, oldValue, newValue ->
        if (oldValue == newValue) {
            return@observable
        }
        if (musicItems.isNotEmpty()) {
            // 如果到了第一首 点上一首 那么往第0个位置插
            if (pointer == 0) {
                historyPosition.add(0, newValue)
            }
            // 如果到了最后一条 继续下一首 那么往最后的位置插
            if (pointer == historyPosition.size) {
                pointer++
                historyPosition.add(newValue)
            }
            if (oldValue >= 0) {
                musicItems[oldValue].isPlaying = false
            }
            musicItems[newValue].isPlaying = true
            position.value = newValue
            oldPosition = newValue
            player.seekTo(newValue, 0)
            player.play()
        }
    }

    /**
     * 切换播放状态
     */
    fun switch() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    /**
     * 指定播放位置
     * @param position Int 当前第[position]个item
     */
    fun play(position: Int) {
        if (nowPosition != position) {
            nowPosition = position
        }
    }

    /**
     * 点击控制按钮更改播放状态 或 更改歌单改变控制按钮状态
     */
    var isPlaying: MutableLiveData<Boolean> = MutableLiveData(false)

    /**
     * 播放列表
     */
    val musicItems : LiveArrayList<MusicModel> = LiveArrayList(arrayListOf(
        MusicModel(path = "https://vodkgeyttp8.vod.126.net/cloudmusic/7464/core/89bf/1941db1b6e25b201bdaa4186f0670f52.mp4?wsSecret=2897c3d4a23dbec89a40300409ecb904&wsTime=1688204180", name = "Hello", singer = "artistName", coverPath = "http://p1.music.126.net/AiGAvupmUbL-hNQfsfSfeQ==/109951168021305745.jpg")
    ))

    val sessionIdListener: MutableLiveData<Int> = MutableLiveData()

    /**
     * 播放器
     */
    var player: ExoPlayer = ExoPlayer.Builder(CommonApplication.getApplication()).build().apply {
        repeatMode = Player.REPEAT_MODE_OFF
        musicItems.forEach {
            addMediaItem(it.mediaItem)
        }
        addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                this@MusicViewModel.isPlaying.value = isPlaying
            }
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
     * 切换播放模式
     */
    fun switchMode() {
        repeatMode.value = ++repeatCount % (Player.REPEAT_MODE_ALL + 1)
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
     * 播放历史记录
     */
    val historyPosition: ArrayList<Int> = arrayListOf()

    /**
     * 上一首
     */
    fun previous() {
        if (pointer > 0) {
            pointer--
        }
        player.pause()
        nextPosition(false, isNext = false)
    }

    /**
     * 下一首
     */
    fun playerNext(isAuto: Boolean) {
        pointer++
        player.pause()
        nextPosition(isAuto)
    }

    /**
     * 计算下一首歌position
     * 需要综合判断：自动下一首、手动上/下一首、[pointer]在0时上一首、[pointer]在末尾时下一首、[pointer]在中间时自动/手动上/下一首
     * @param isAuto Boolean 是否自动
     * @param random Boolean 是否随机
     * @param isNext Boolean 是否下一首
     */
    private fun nextPosition(isAuto: Boolean, random: Boolean = false, isNext : Boolean = true) {
        var position = 0
        if (isAuto) {
            // 自动下一首
            if (pointer != historyPosition.size) {
                position = historyPosition[pointer]
                play(position)
                return
            }
            position = when (repeatMode.value) {
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
            // 手动点上一首/下一首
            if (pointer == 0 || pointer == historyPosition.size) {
                nextPosition(true, random)
                return
            } else {
                position = historyPosition[pointer - if (isNext) -1 else 1]
            }
        }
        play(position)
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