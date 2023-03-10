package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.adapter.PlayerBottomAdapter
import com.aliyunm.musicplayer.databinding.FragmentPlayerBottomBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                viewBinding.rvMusicList.scrollToPosition(viewModel.nowPosition)
            } else {
                isThis = false
            }
            viewBinding.rvMusicList.adapter?.notifyItemChanged(viewModel.oldPosition)
            viewBinding.rvMusicList.adapter?.notifyItemChanged(viewModel.nowPosition)
        }
        viewModel.progressListener.observe(this) {
            val progress = ((((it + 0f) / viewModel.player.duration) * 100)).toInt()
            viewBinding.btnPlayerStatus.progress = progress
        }
        viewModel.isPlaying.observe(this) {
            if (it) {
                (viewBinding.rvMusicList.adapter as PlayerBottomAdapter).resume()
            } else {
                (viewBinding.rvMusicList.adapter as PlayerBottomAdapter).pause()
            }
            viewBinding.btnPlayerStatus.switchPlayStatus(it)
        }
    }

    var isThis = true

    override fun initView() {
        viewBinding.apply {
            btnPlayerStatus.setClickListener {
                viewModel.isFirst = false
                viewModel.btn()
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
                    if (viewModel.isRecordAudio) {
                        viewModel.playerPopup.show()
                    }
                }
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == SCROLL_STATE_IDLE) {
                            isThis = true
                            val itemView = snapHelper.findSnapView(recyclerView.layoutManager)
                            val position = if (itemView == null) -1 else getChildAdapterPosition(itemView)
                            viewModel.scroll(position)
                        }
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        viewModel.isFirst = false
                    }
                })
            }
        }

        viewModel.apply {

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
        }
    }
}