package com.aliyunm.musicplayer.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewClientCompat
import com.aliyunm.common.textChangeFlow
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.databinding.FragmentDiscoverBinding
import com.aliyunm.musicplayer.http.Service
import com.aliyunm.musicplayer.model.MusicModel
import com.aliyunm.musicplayer.viewmodel.MusicViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, MusicViewModel>() {
    override fun setBinding(): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    private fun searchFlow(key: String) = flow { emit(search(key)) }

    private suspend fun search(key: String): ArrayList<MusicModel> {
        return Service.searchMusic(key)?.records ?: arrayListOf()
    }

    override fun initView() {
        // viewBinding.webView.apply {
        //
        //     val assetLoader = WebViewAssetLoader.Builder()
        //         .addPathHandler("/assets/", AssetsPathHandler(requireContext()))
        //         .build()
        //
        //     webViewClient = object : WebViewClientCompat() {
        //
        //         override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? {
        //             return assetLoader.shouldInterceptRequest(request.url)
        //         }
        //
        //         override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        //             return assetLoader.shouldInterceptRequest(Uri.parse(url))
        //         }
        //
        //         override fun onPageFinished(view: WebView?, url: String?) {
        //             super.onPageFinished(view, url)
        //         }
        //     }
        //
        //     settings.apply {
        //         defaultTextEncodingName = "UTF-8"
        //         javaScriptEnabled = true
        //         allowUniversalAccessFromFileURLs = false
        //         allowFileAccess = false
        //         allowContentAccess = false
        //         mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        //     }
        //
        //     loadUrl("https://appassets.androidplatform.net/assets/encSecKey.html")
        // }
        viewBinding.etSearch.apply {
            textChangeFlow()
                .filter { it.isNotEmpty() }
                .debounce(300)
                .flatMapLatest {
                    searchFlow(it.toString())
                }
                .flowOn(Dispatchers.IO)
                .onEach { musicModels ->
                    musicModels.forEach {
                        println(it.toJson())
                    }
                }
                .launchIn(MainScope())
        }
        viewBinding.rvMusicList.apply {

        }
    }

    // fun generate(id : String) {
    //     viewBinding.webView.evaluateJavascript("javascript:generate('$id')", object : ValueCallback<String> {
    //         override fun onReceiveValue(value: String) {
    //             println(value)
    //         }
    //     })
    // }
}