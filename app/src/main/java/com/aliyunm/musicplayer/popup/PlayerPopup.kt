package com.aliyunm.musicplayer.popup

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.setBlurTransformation
import com.aliyunm.common.setImage
import com.aliyunm.common.utils.DisplayUtils
import com.aliyunm.common.utils.ScreenUtils.getNavigationBarHeight
import com.aliyunm.common.utils.ScreenUtils.getScreenHeight
import com.aliyunm.common.utils.ScreenUtils.getStatusBarHeight
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.databinding.PopupPlayerBinding
import com.aliyunm.musicplayer.http.Service
import com.aliyunm.musicplayer.viewmodel.MusicViewModel
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerPopup(activity: ComponentActivity) : BaseBottomPopup<PopupPlayerBinding>(activity) {

    private lateinit var viewModel : MusicViewModel

    override fun initData() {
        viewModel = CommonApplication.getApplication().getViewModel(MusicViewModel::class.java)
        viewBinding.visualizerView.setVisualizer(viewModel.player.audioSessionId)
        viewModel.position.observe(getActivity()) {
            setBackground()
        }
        viewModel.progressListener.observe(getActivity()) {
            viewBinding.tvTime.text = time(it.toLong())
            viewBinding.tvCountTime.text = time(viewModel.player.duration)
        }
    }

    private fun setTypeIcon(type : Int, view : ImageView) {
        val drawable : Int = when(type) {
            Player.REPEAT_MODE_OFF -> {
                R.drawable.ic_cycle_random
            }
            Player.REPEAT_MODE_ONE -> {
                R.drawable.ic_cycle_single
            }
            Player.REPEAT_MODE_ALL -> {
                R.drawable.ic_cycle_list
            }
            else -> {
                R.drawable.ic_cycle_list
            }
        }
        view.setImageResource(drawable)
    }

    override fun initView() {
        viewModel.repeatMode.observe(getActivity()) {
            setTypeIcon(it, viewBinding.ivCycle)
        }
        viewModel.isPlaying.observe(getActivity()) {
            val drawable : Int = if (it) {
                R.drawable.ic_pause_circle
            } else {
                R.drawable.ic_play_circle
            }
            viewBinding.ivPausePlay.setImageResource(drawable)
        }
        viewBinding.apply {
            ivCycle.apply {
                setTypeIcon(viewModel.repeatMode.value!!, this)
                setOnClickListener {
                    ++viewModel.repeatCount
                }
            }

            ivPrevious.setOnClickListener {
                viewModel.previous()
            }

            ivNext.setOnClickListener {
                viewModel.playerNext(false)
            }

            ivClose.setOnClickListener {
                dismiss()
            }

            ivSongList.setOnClickListener {
                viewModel.musicListPopup.show()
            }

            ivPausePlay.setOnClickListener {
                viewModel.isFirst = false
                viewModel.btn()
            }

            tvCountTime.apply {
                text = time(viewModel.player.duration)
            }
        }
        viewBinding.root.apply {
            layoutParams.apply {
                setPadding(paddingLeft, paddingTop, paddingRight, getNavigationBarHeight(context))
            }
        }
    }

    private fun time(ms : Long) : String {
        val m = ms / 1000 / 60
        val s = ms / 1000 % 60
        return "${ if (m < 10) "0${m}" else m }:${ if (s < 10) "0${s}" else s }"
    }

    override fun getBinding(): PopupPlayerBinding {
        return PopupPlayerBinding.inflate(LayoutInflater.from(getActivity()), null, false)
    }

    override fun show(isDark : Boolean) {
        setBackground()
        viewBinding.visualizerView.setVisualizerEnabled(true)
        val height: Int = getScreenHeight(getActivity())
        super.show(LinearLayout.LayoutParams.MATCH_PARENT, height, false)
    }

    private fun setBackground() {
        Service.getImage(viewModel.musicItems[viewModel.nowPosition].coverPath) {
            CoroutineScope(Dispatchers.Main).launch {
                viewBinding.visualizerView.setMusicCover(it)
                viewBinding.ivMusicCover.setBlurTransformation(it, 170f)
            }
        }
    }

    override fun dismiss() {
        super.dismiss()
        viewBinding.visualizerView.setVisualizerEnabled(false)
    }
}