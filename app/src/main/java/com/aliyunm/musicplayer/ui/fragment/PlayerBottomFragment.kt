package com.aliyunm.musicplayer.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.PagerSnapHelper
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.adapter.PlayerBottomAdapter
import com.aliyunm.musicplayer.databinding.FragmentPlayerBottomBinding
import com.aliyunm.musicplayer.ui.activity.PlayerActivity
import com.aliyunm.musicplayer.viewmodel.MusicViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerControlView

class PlayerBottomFragment : BaseFragment<FragmentPlayerBottomBinding, MusicViewModel>() {

    override fun setBinding(): FragmentPlayerBottomBinding {
        return FragmentPlayerBottomBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.player.apply {
            viewModel.musicItems.forEach {
                addMediaItem(it.mediaItem)
            }
            repeatMode = viewModel.repeatMode
            prepare()
        }

        viewBinding.rvMusicList.apply {
            adapter = PlayerBottomAdapter(viewModel.musicItems)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }

        viewModel.musicItems.apply {
            removeAt.observe(requireActivity()) { position ->
                viewBinding.rvMusicList.adapter?.notifyItemRemoved(position)
                viewBinding.rvMusicList.adapter?.notifyItemRangeChanged(position, viewModel.musicItems.size)
            }

            clear.observe(requireActivity()) {
                if (it) {
                    viewBinding.rvMusicList.adapter?.notifyDataSetChanged()
                    parentFragmentManager.beginTransaction()
                        .hide(this@PlayerBottomFragment)
                        .commit()
                }
            }
        }

        viewBinding.ivMusicList.apply {
            setOnClickListener {
                viewModel.musicListPopup.show()
            }
        }

        viewModel.playerControlView.apply {
            post {
                setProgressUpdateListener(object : PlayerControlView.ProgressUpdateListener {
                    override fun onProgressUpdate(position: Long, bufferedPosition: Long) {
                        viewBinding.btnPlayerStatus.progress = position.toInt()
                    }
                })
                player = viewModel.player
            }
        }
    }

    override fun initView() {
        viewBinding.root.setOnClickListener {
            startActivity(Intent(requireContext(), PlayerActivity::class.java))
        }
        viewModel.player.addListener(object : Player.Listener {

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when(playbackState) {
                    Player.STATE_BUFFERING  -> {

                    }
                    Player.STATE_ENDED      -> {
                        viewBinding.btnPlayerStatus.changeIsPlayStatus()
                    }
                    Player.STATE_IDLE       -> {

                    }
                    Player.STATE_READY      -> {

                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                viewBinding.btnPlayerStatus.progress = 0
            }
        })
        viewBinding.btnPlayerStatus.setClickListener {
            if (it) {
                viewModel.player.pause()
            } else {
                viewModel.player.play()
            }
        }
    }
}