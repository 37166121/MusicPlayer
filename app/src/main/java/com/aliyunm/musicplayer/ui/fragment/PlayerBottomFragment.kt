package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.adapter.PlayerBottomAdapter
import com.aliyunm.musicplayer.databinding.FragmentPlayerBottomBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel

class PlayerBottomFragment : BaseFragment<FragmentPlayerBottomBinding, MusicViewModel>() {

    private lateinit var bottomAdapter: PlayerBottomAdapter

    override fun setBinding(): FragmentPlayerBottomBinding {
        return FragmentPlayerBottomBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {
        bottomAdapter = PlayerBottomAdapter(viewModel.musicItems) {
            viewModel.nowPosition = it
            viewModel.playerPopup.show()
        }
    }

    override fun initView() {
        viewModel.isPlaying.observe(requireActivity()) {
            viewBinding.btnPlayerStatus.switchPlayStatus(it)
        }
        viewBinding.apply {
            rvMusicList.apply {
                adapter = bottomAdapter
            }
            ivMusicList.setOnClickListener {
                viewModel.musicListPopup.show()
            }
            btnPlayerStatus.setClickListener {
                viewModel.switch()
            }
        }
    }
}