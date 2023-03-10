package com.aliyunm.musicplayer.http

import com.aliyunm.musicplayer.BuildConfig
import com.aliyunm.musicplayer.http.Path.BASEURL
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Config {

    /**
     * 配置Retrofit
     */
    val API: Api by lazy {
        val client = getOkHttpClient()
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(Api::class.java)
    }

    /**
     * 配置okhttp
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(HttpInterceptor)
            .retryOnConnectionFailure(true)
            .cookieJar(CookieJarManage)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            builder.addInterceptor(httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }

    /**
     * cookie管理器
     */
    private object CookieJarManage : CookieJar {
        private val cookieStore: HashMap<String, List<Cookie>> = HashMap()

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val cookies = cookieStore[url.host]
            return cookies ?: ArrayList()
        }

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host] = cookies
        }

    }

    /**
     * 拦截器
     */
    private object HttpInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()
            val request: Request = original.newBuilder().build()
            return chain.proceed(request)
        }
    }
}