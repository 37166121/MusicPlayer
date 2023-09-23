package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import com.aliyunm.common.startActivity
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.common.utils.ThemeUtils
import com.aliyunm.musicplayer.adapter.MusicListAdapter
import com.aliyunm.musicplayer.databinding.FragmentDiscoverBinding
import com.aliyunm.musicplayer.databinding.SettingsActivityBinding
import com.aliyunm.musicplayer.ui.activity.SettingsActivity
import com.aliyunm.musicplayer.viewmodel.MusicViewModel

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, MusicViewModel>() {

    private lateinit var musicListAdapter : MusicListAdapter

    override fun setBinding(): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {
        musicListAdapter = MusicListAdapter(viewModel.musicItems) {
            viewModel.nowPosition = it
        }
    }

    override fun initView() {
        viewBinding.rvMusicList.apply {
            adapter = musicListAdapter
        }
        viewBinding.ivMenu.setOnClickListener {
            requireContext().startActivity<SettingsActivity>()
        }
    }
}