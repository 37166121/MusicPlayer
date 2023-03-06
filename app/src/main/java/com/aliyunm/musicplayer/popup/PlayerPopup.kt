package com.aliyunm.musicplayer.popup

import android.content.res.Resources
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.ScreenUtils.getNavigationBarHeight
import com.aliyunm.common.utils.ScreenUtils.getScreenHeight
import com.aliyunm.common.utils.ScreenUtils.getStatusBarHeight
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.databinding.PopupPlayerBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel

class PlayerPopup(activity: ComponentActivity) : BaseBottomPopup<PopupPlayerBinding>(activity) {

    private lateinit var musicViewModel : MusicViewModel

    override fun initData() {
        musicViewModel = CommonApplication.getApplication().getViewModel(MusicViewModel::class.java)
    }

    override fun initView() {
        isClippingEnabled = false
        viewBinding.apply {
            ivClose.setOnClickListener {
                dismiss()
            }

            ivSongList.setOnClickListener {
                musicViewModel.musicListPopup.show()
            }
        }
        viewBinding.root.apply {
            layoutParams.apply {
                setPadding(paddingLeft, paddingTop, paddingRight, getNavigationBarHeight(getContext()))
            }
        }
    }

    override fun getBinding(): PopupPlayerBinding {
        return PopupPlayerBinding.inflate(LayoutInflater.from(getActivity()), null, false)
    }

    override fun show(isDark : Boolean) {
        // getActivity().setTheme(R.style.Theme_MusicPlayer_Light)
        // getActivity().recreate()
        val height: Int = getScreenHeight(getActivity()) + getStatusBarHeight(getContext()) + getNavigationBarHeight(getContext())
        super.show(LinearLayout.LayoutParams.MATCH_PARENT, height, false)
    }

    override fun dismiss() {
        super.dismiss()
        // getActivity().setTheme(R.style.Theme_MusicPlayer)
        // getActivity().recreate()
    }
}