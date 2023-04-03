package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.databinding.FragmentDiscoverBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, MusicViewModel>() {
    override fun setBinding(): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        viewBinding.rvMusicList.apply {

        }
    }
}