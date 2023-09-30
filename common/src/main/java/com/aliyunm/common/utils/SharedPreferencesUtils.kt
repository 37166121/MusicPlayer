package com.aliyunm.common.utils

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import java.lang.Exception

object SharedPreferencesUtils {
    private lateinit var mSharedPreferences : SharedPreferences
    private lateinit var mEditor : Editor

    const val POSITION = "position"

    /**
     * 用户授权令牌，可在用户 Api 中调用获取
     * @see <a href="https://developer.music.163.com/st/developer/">云音乐开放平台</a>
     */
    const val ACCESSTOKEN = "accessToken"

    /**
     * 应用 id 值，可从“管理中心” - “我的应用” - “查看和修改”中查看
     * @see <a href="https://developer.music.163.com/st/developer/">云音乐开放平台</a>
     */
    const val APPID = "appId"

    fun setSharedPreferences(sharedPreferences : SharedPreferences) {
        mSharedPreferences = sharedPreferences
        mEditor = mSharedPreferences.edit()
    }

    fun getSharedPreferences() : SharedPreferences {
        return mSharedPreferences
    }

    fun putString(key : String, value : String) : SharedPreferencesUtils {
        mEditor.putString(key, value)
        commit()
        return this
    }

    fun putStringSet(key : String, values : Set<String>) : SharedPreferencesUtils {
        mEditor.putStringSet(key, values)
        commit()
        return this
    }

    fun putInt(key : String, value : Int) : SharedPreferencesUtils {
        mEditor.putInt(key, value)
        commit()
        return this
    }

    fun putLong(key : String, value : Long) : SharedPreferencesUtils {
        mEditor.putLong(key, value)
        commit()
        return this
    }

    fun putFloat(key : String, value : Float) : SharedPreferencesUtils {
        mEditor.putFloat(key, value)
        commit()
        return this
    }

    fun putBoolean(key : String, value : Boolean) : SharedPreferencesUtils {
        mEditor.putBoolean(key, value)
        commit()
        return this
    }

    fun commit(): Boolean {
        return mEditor.commit()
    }

    fun apply() {
        mEditor.apply()
    }

    fun getAll(): Map<String, *> {
        return mSharedPreferences.all
    }

    fun getString(key: String, defValue: String, callback : (String) -> Unit = {}): String {
        return mSharedPreferences.getString(key, defValue) ?: throw Exception("未知key或value为null")
    }

    fun getStringSet(key: String, defValues: Set<String>, callback : (MutableSet<String>) -> Unit = {}): Set<String> {
        return mSharedPreferences.getStringSet(key, defValues) ?: throw Exception("未知key或value为null")
    }

    fun getInt(key: String, defValue: Int, callback : (Int) -> Unit = {}): Int {
        return mSharedPreferences.getInt(key, defValue)
    }

    fun getLong(key: String, defValue: Long, callback : (Long) -> Unit = {}): Long {
        return mSharedPreferences.getLong(key, defValue)
    }

    fun getFloat(key: String, defValue: Float, callback : (Float) -> Unit = {}): Float {
        return mSharedPreferences.getFloat(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean, callback : (Boolean) -> Unit = {}): Boolean {
        return mSharedPreferences.getBoolean(key, defValue)
    }

    fun contains(key: String): Boolean {
        return mSharedPreferences.contains(key)
    }

    fun remove(vararg key: String) {
        key.iterator().forEach {
            mEditor.remove(it)
        }
        commit()
    }

}