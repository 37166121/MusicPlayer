package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.adapter.PlayerBottomAdapter
import com.aliyunm.musicplayer.databinding.FragmentPlayerBottomBinding
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
        viewModel.position.observe(this) {
            if (!isThis) {
                viewBinding.rvMusicList.scrollToPosition(it)
            } else {
                isThis = false
            }
            viewBinding.rvMusicList.adapter?.notifyItemChanged(it)
        }
        viewModel.isPlaying.observe(this) {
            if (it) {
                (viewBinding.rvMusicList.adapter as PlayerBottomAdapter).pause()
                viewModel.player.pause()
            } else {
                (viewBinding.rvMusicList.adapter as PlayerBottomAdapter).resume()
                viewModel.player.play()
            }
            viewBinding.btnPlayerStatus.switchPlayStatus(it)
        }
    }

    var isThis = true

    override fun initView() {
        viewBinding.apply {
            btnPlayerStatus.setClickListener {
                viewModel.isPlaying.value = it
            }
            ivMusicList.apply {
                setOnClickListener {
                    viewModel.musicListPopup.show()
                }
            }
            rvMusicList.apply {
                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(this)
                adapter = PlayerBottomAdapter(viewModel.musicItems) {
                    viewModel.playerPopup.show()
                }
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == SCROLL_STATE_IDLE) {
                            isThis = true
                            val itemView = snapHelper.findSnapView(recyclerView.layoutManager)
                            val position = if (itemView == null) -1 else getChildAdapterPosition(itemView)
                            if (viewModel.nowPosition != position) {
                                viewModel.nowPosition = position
                            }
                        }
                    }
                })
            }
        }

        viewModel.apply {
            player.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    when(playbackState) {
                        Player.STATE_BUFFERING  -> {

                        }
                        Player.STATE_ENDED      -> {

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

            musicItems.apply {

                event.observe(requireActivity()) {
                    if (size == 0) {
                        viewBinding.rvMusicList.adapter?.notifyDataSetChanged()
                        parentFragmentManager.beginTransaction()
                            .hide(this@PlayerBottomFragment)
                            .commit()
                    } else {
                        parentFragmentManager.beginTransaction()
                            .show(this@PlayerBottomFragment)
                            .commit()
                        viewBinding.rvMusicList.adapter?.notifyDataSetChanged()
                    }
                }

                removeAt.observe(requireActivity()) { position ->
                    viewBinding.rvMusicList.adapter?.notifyItemRemoved(position)
                    viewBinding.rvMusicList.adapter?.notifyItemRangeChanged(position, viewModel.musicItems.size)
                }
            }

            playerControlView.apply {
                player = viewModel.player
                post {
                    setProgressUpdateListener(object : PlayerControlView.ProgressUpdateListener {
                        override fun onProgressUpdate(position: Long, bufferedPosition: Long) {
                            viewBinding.btnPlayerStatus.progress = position.toInt()
                        }
                    })
                }
            }
        }
    }
}