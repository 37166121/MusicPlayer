package com.aliyunm.musicplayer.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.exoplayer2.MediaItem

@Entity(tableName = "music")
data class MusicModel(
    @PrimaryKey val id : String = "",
    /**
     * 歌名
     */
    val name : String = "",
    /**
     * 歌曲路径
     */
    val path : String,
    /**
     * 网络路径或者本地路径
     */
    val type : Int = MusicType.LOCAL,
    /**
     * 歌曲来源
     */
    val source : String = "",
    /**
     * 歌词路径
     */
    val lyricsPath : String = "",
    /**
     * 歌词路径(翻译)
     */
    val lyricsTranslatePath : String = "",
    /**
     * 封面路径
     */
    val coverPath : String = "",
    /**
     * 作曲 逗号分割 ,
     */
    val composing : String = "",
    /**
     * 填词 逗号分割 ,
     */
    val lyricist : String = "",
    /**
     * 填词(翻译)
     */
    val lyricistTranslate : String = "",
    /**
     * 歌手 逗号分割 ,
     */
    val singer : String = "",
    /**
     * 专辑 逗号分割 ,
     */
    var isPlaying : Boolean = false,
) : BaseModel() {
    @Ignore val album : AlbumModel = AlbumModel()
    var alg : String = ""
    @Ignore var artists : ArrayList<ArtistModel> = arrayListOf()
    var coverImgUrl : String = ""
    var dlLevel : String = ""
    var downloadFlag : Boolean = false
    var duration : Int = -1
    var liked : Boolean = false
    var maxBrLevel : String = ""
    var payDownloadFlag : Boolean = false
    var payPlayFlag : Boolean = false
    var plLevel : String = ""
    var playFlag : Boolean = false
    var privateCloudSong : Boolean = false
    var songMaxBr : Int = -1
    var songTag : String = ""
    var userMaxBr : Int = -1
    var vipFlag : Boolean = false
    var vipPlayFlag : Boolean = false
    var visible : Boolean = false

    @Ignore val mediaItem : MediaItem = MediaItem.fromUri(path)

    object MusicType {
        /**
         * 本地路径
         */
        const val LOCAL = 0x0001

        /**
         * 网络路径
         */
        const val NETWORK = 0x0002
    }

    fun getSingerList() : List<String> {
        return singer.split(",")
    }
}