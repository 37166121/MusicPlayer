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

    override fun initData() {

    }

    override fun initView() {

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
}