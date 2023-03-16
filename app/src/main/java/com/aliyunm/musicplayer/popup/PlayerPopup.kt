package com.aliyunm.musicplayer.popup

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.setBlurTransformation
import com.aliyunm.common.setImage
import com.aliyunm.common.utils.GlideUtils
import com.aliyunm.common.utils.ScreenUtils.getNavigationBarHeight
import com.aliyunm.common.utils.ScreenUtils.getScreenHeight
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
    private lateinit var operatingAnim: ObjectAnimator

    override fun initData() {
        viewModel = CommonApplication.getApplication().getViewModel(MusicViewModel::class.java).apply {

            musicItems.clear.observe(getActivity()) {
                dismiss()
            }

            position.observe(getActivity()) {
                setBackground()
                viewBinding.tvMusicName.text = musicItems[it].name
                operatingAnim.cancel()
                operatingAnim.start()
            }
            progressListener.observe(getActivity()) {
                viewBinding.tvTime.text = time(it.toLong())
                viewBinding.tvCountTime.text = time(player.duration)
            }
        }

        viewBinding.visualizerView.setVisualizer(viewModel.player.audioSessionId)

        operatingAnim = ObjectAnimator.ofFloat(viewBinding.ivMusicCoverCenter, "rotation", 0f, 359f).apply {
            duration = 15 * 1000
            repeatCount = -1
            interpolator = LinearInterpolator()
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
                resume()
                R.drawable.ic_pause_circle
            } else {
                pause()
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
        val t = if (ms < 0) 0 else ms
        val m = t / 1000 / 60
        val s = t / 1000 % 60
        return "${ if (m < 10) "0${m}" else m }:${ if (s < 10) "0${s}" else s }"
    }

    override fun getBinding(): PopupPlayerBinding {
        return PopupPlayerBinding.inflate(LayoutInflater.from(getActivity()), null, false)
    }

    override fun show(isDark : Boolean) {
        setBackground()
        getActivityWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        viewBinding.tvMusicName.text = viewModel.musicItems[viewModel.nowPosition].name
        viewBinding.visualizerView.setVisualizerEnabled(true)
        val height: Int = getScreenHeight(getActivity())
        super.show(LinearLayout.LayoutParams.MATCH_PARENT, height, false)
    }

    private fun setBackground() {
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = Service.getImage(viewModel.musicItems[viewModel.nowPosition].coverPath)
            viewBinding.visualizerView.setMusicCover(bitmap)
            viewBinding.ivMusicCover.setBlurTransformation(bitmap, 170f)
            viewBinding.ivMusicCoverCenter.apply {
                layoutParams.apply {
                    width = viewBinding.visualizerView.getR().toInt()
                    height = viewBinding.visualizerView.getR().toInt()
                }
                setImage(bitmap, GlideUtils.CircleCrop, 999)
            }
        }
    }

    override fun dismiss() {
        super.dismiss()
        getActivityWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        viewBinding.visualizerView.setVisualizerEnabled(false)
    }

    private fun pause() {
        if (operatingAnim.isStarted) {
            operatingAnim.pause()
        }
    }

    private fun resume() {
        if (operatingAnim.isStarted) {
            operatingAnim.resume()
        } else {
            operatingAnim.start()
        }
    }
}