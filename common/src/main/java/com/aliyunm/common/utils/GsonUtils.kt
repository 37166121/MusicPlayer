package com.aliyunm.common.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.lang.reflect.Type

object GsonUtils {
    fun toJson(any: Any): String {
        val gson: Gson = Gson()
        return gson.toJson(any)
    }

    fun<T> fromJson(json : String, classOfT : Class<T>) : T {
        val gson : Gson = Gson()
        return gson.fromJson(json, classOfT)
    }

    fun<T> fromJson(json : String, typeToken : TypeToken<T>) : T {
        return fromJson(json, typeToken.type)
    }

    fun<T> fromJson(json : Reader, typeToken : TypeToken<T>) : T {
        return fromJson(json, typeToken.type)
    }

    fun<T> fromJson(json : String, type : Type) : T {
        val gson : Gson = Gson()
        return gson.fromJson(json, type)
    }

    fun<T> fromJson(json : Reader, type : Type) : T {
        val gson : Gson = Gson()
        return gson.fromJson(json, type)
    }
}