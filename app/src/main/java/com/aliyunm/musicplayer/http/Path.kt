package com.aliyunm.musicplayer.http

object Path {
    const val BASEURL = "http://openapi.music.163.com/openapi/"

    const val REQUEST = "growth/external/sandbox/request"

    /// 搜索

    /**
     * 搜索歌曲
     */
    const val SEARCH_SONG = "music/basic/search/song/get/v2"

    /**
     * 搜索歌单
     */
    const val SEARCH_PLAYLIST = "music/basic/search/playlist/get/v2"

    /**
     * 搜索专辑
     */
    const val SEARCH_ALBUM = "music/basic/search/album/get/v2"

    /**
     * 根据标签搜索歌单
     */
    const val SEARCH_PLAYLIST_BY_TAG = "music/basic/search/playlist/bytag/get/v2"

    /**
     * 根据艺人关键字搜索歌曲
     */
    const val SEARCH_SONG_BY_ARTIST = "music/basic/search/song/byartist/get/v2"

    /**
     * 获取歌单标签
     */
    const val PLAYLIST_TAG = "music/basic/playlist/tag/get/v2"

    /**
     * 根据艺人、专辑关键字搜索专辑列表
     */
    const val SEARCH_SONG_BY_ALBUM = "music/basic/search/song/by/album/artist/get/v2"

    /**
     * 根据关键字搜索艺人
     */
    const val SEARCH_ARTISTS = "music/basic/search/artists/get/v2"

    /**
     * 根据歌曲名、艺人名搜索歌曲信息
     */
    const val SEARCH_SONG_BY_ARTIST_SONG = "music/basic/search/song/by/artist/song/get/v2"

    /**
     * 获取热词
     */
    const val SEARCH_HOT = "music/basic/search/hot/keyword/get/v2"

    /// 歌单

    /**
     * 获取用户收藏的歌单列表
     */
    const val PLAYLIST_SUBED = "music/basic/playlist/subed/get/v2"

    /**
     * 获取歌单里的歌曲列表
     */
    const val PLAYLIST_SONG = "music/basic/playlist/song/list/get/v2"

    /**
     * 获取用户红心歌单
     */
    const val PLAYLIST_STAR = "music/basic/playlist/star/get/v2"

    /**
     * 红心歌单添加或删除歌曲
     */
    const val PLAYLIST_SONG_LIKE = "music/basic/playlist/song/like/v2"

    /**
     * 获取用户创建歌单列表
     */
    const val PLAYLIST_CREATED = "music/basic/playlist/created/get/v2"

    /**
     * 获取歌单详情
     */
    const val PLAYLIST_DETAIL = "music/basic/playlist/detail/get/v2"

    /**
     * 收藏歌单
     */
    const val PLAYLIST_SUB = "music/basic/playlist/sub/v2"

    /**
     * 取消收藏歌单
     */
    const val PLAYLIST_UNSUB = "music/basic/playlist/unsub/v2"

    /**
     * 获取榜单列表
     */
    const val TOP_LIST = "music/basic/toplist/get/v2"

    /// 关于歌曲

    /**
     * 批量获取歌曲信息列表
     */
    const val SONG_LIST = "music/basic/song/list/get/v2"

    /**
     * 获取歌词
     */
    const val SONG_LYRIC = "music/basic/song/lyric/get/v2"

    /**
     * 获取歌曲播放url
     */
    const val SONG_PLAY_URL = "music/basic/song/playurl/get/v2"

    /**
     * 获取歌曲播放toast文案
     */
    const val SONG_TEXT_PLAY = "music/basic/song/text/play/get/v2"

    /**
     * 获取歌曲详情
     */
    const val SONG_DETAIL = "music/basic/song/detail/get/v2"

    /**
     * 获取歌曲下载toast文案
     */
    const val SONG_TEXT_DOWNLOAD = "music/basic/song/text/download/get/v2"

    /**
     * 获取歌曲下载url
     */
    const val SONG_DOWNLOAD_URL = "music/basic/song/downloadurl/get/v2"

    /**
     * 获取用户收藏的艺人
     */
    const val ARTIST_SUBED = "music/basic/artist/subed/get/v2"

    /**
     * 根据分类获取艺人列表
     */
    const val ARTISTS_LIST = "music/basic/artists/list/bytype/get/v2"

    /**
     * 获取艺人下的热门歌曲列表
     */
    const val ARTISTS_HOT_SONG = "music/basic/artists/hotsong/list/get/v2"

    /**
     * 获取专辑下的歌曲列表
     */
    const val ALBUM_SONG = "music/basic/album/song/list/get/v2"

    /// 推荐

    /**
     * 歌单推荐
     */
    const val RECOMMEND_PLAYLIST = "music/basic/recommend/playlist/get"

    /**
     * 每日推荐
     */
    const val RECOMMEND_SONGLIST = "music/basic/recommend/songlist/get/v2"

    /// 用户

    /**
     * 匿名登录
     */
    const val LOGIN = "music/basic/oauth2/login/anonymous"

}