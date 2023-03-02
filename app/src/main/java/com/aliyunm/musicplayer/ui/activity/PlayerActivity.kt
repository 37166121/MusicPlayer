package com.aliyunm.musicplayer.ui.activity

import android.os.Bundle
import com.aliyunm.common.ui.BaseActivity
import com.aliyunm.musicplayer.databinding.ActivityPlayerBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel

class PlayerActivity : BaseActivity<ActivityPlayerBinding, MusicViewModel>() {
    override fun setBinding(): ActivityPlayerBinding {
        return ActivityPlayerBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel
    }

    override fun initView() {

    }
}