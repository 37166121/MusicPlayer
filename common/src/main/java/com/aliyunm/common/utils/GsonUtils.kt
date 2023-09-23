package com.aliyunm.common.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.lang.reflect.Type

object GsonUtils {
    fun toJson(any: Any): String {
        return Gson().toJson(any)
    }

    inline fun<reified T> fromJson(json : String) : T {
        return fromJson(json, T::class.java)
    }

    fun<T> fromJson(json : Any, typeToken : TypeToken<T>) : T {
        return fromJson(json, typeToken.type)
    }

    fun<T> fromJson(json : Any, type : Type) : T {
        return when(json) {
            is String -> {
                fromJson(json, type)
            }

            is Reader -> {
                fromJson(json, type)
            }

            else -> {
                fromJson(toJson(json), object : TypeToken<Any>(){}.type)
            }
        }
    }

    fun<T> fromJson(json : String, clazz : Class<T>) : T {
        return Gson().fromJson(json, clazz)
    }

    fun<T> fromJson(json : String, type : Type) : T {
        return Gson().fromJson(json, type)
    }

    fun<T> fromJson(json : Reader, type : Type) : T {
        return Gson().fromJson(json, type)
    }
}