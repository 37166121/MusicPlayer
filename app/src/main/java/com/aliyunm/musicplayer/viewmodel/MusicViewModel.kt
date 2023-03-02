package com.aliyunm.musicplayer.viewmodel

import android.media.browse.MediaBrowser
import android.media.session.MediaController
import androidx.lifecycle.ViewModel
import com.aliyunm.musicplayer.LiveArrayList
import com.aliyunm.musicplayer.model.MusicModel
import com.aliyunm.musicplayer.popup.MusicListPopup
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerControlView

class MusicViewModel : ViewModel() {

    var position : Int = 0

    /**
     * 播放列表
     */
    val musicItems : LiveArrayList<MusicModel> = LiveArrayList(arrayListOf(
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3")),
        MusicModel(mediaItem = MediaItem.fromUri("https://m701.music.126.net/20230302111937/ca8753a2eabe92283cc6fbca32d9e5b8/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/22046760991/6d9b/eb87/f3de/a3cb8fd53c760fb3193b953df03b7531.mp3"))
    ))

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

    lateinit var mediaController : MediaController
    lateinit var mediaBrowser : MediaBrowser

    lateinit var musicListPopup : MusicListPopup
}