package com.aliyunm.musicplayer.model.apiModel

import com.aliyunm.common.utils.SharedPreferencesUtils
import com.aliyunm.common.utils.SharedPreferencesUtils.ACCESSTOKEN
import com.aliyunm.common.utils.SharedPreferencesUtils.APPID
import com.aliyunm.musicplayer.model.BaseModel
import java.util.Date

data class RequestModel(
    /**
     * 用户授权令牌，可在用户 Api 中调用获取
     * @see <a href="https://developer.music.163.com/st/developer/">云音乐开放平台</a>
     */
    val accessToken : String = SharedPreferencesUtils.getString(ACCESSTOKEN, ""),
    /**
     * 应用 id 值，可从“管理中心” - “我的应用” - “查看和修改”中查看
     * @see <a href="https://developer.music.163.com/st/developer/">云音乐开放平台</a>
     */
    val appId : String = SharedPreferencesUtils.getString(APPID, ""),
    /**
     * 毫秒级时间戳，有效期5分钟
     */
    val timestamp : Long = Date().time,
    /**
     * 接口参数 json
     */
    val bizContentJson : String = "",
    /**
     * 接口地址
     */
    val url : String = ""
) : BaseModel() {

    /**
     * 网易云原始接口 接口重要参数之一[params]
     * @see <a href="https://juejin.cn/post/7023252269952925733">JS逆向之网易云参数params和encSecKey获取</a>
     */
    val params : String = ""

    /**
     * 网易云原始接口 接口重要参数之一[encSecKey]
     * @see <a href="https://juejin.cn/post/7023252269952925733">JS逆向之网易云参数params和encSecKey获取</a>
     */
    val encSecKey : String = ""
}
