package com.aliyunm.musicplayer.ui.activity

import android.os.Bundle
import com.aliyunm.common.textChangeFlow
import com.aliyunm.common.ui.BaseActivity
import com.aliyunm.musicplayer.databinding.ActivitySearchBinding
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

class SearchActivity : BaseActivity<ActivitySearchBinding, MusicViewModel>() {

    override fun setBinding(): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {
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

    private fun searchFlow(key: String) = flow { emit(search(key)) }

    private suspend fun search(key: String): ArrayList<MusicModel> {
        return Service.searchMusic(key)?.records ?: arrayListOf()
    }
}