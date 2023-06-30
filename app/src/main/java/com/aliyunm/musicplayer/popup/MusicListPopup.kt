package com.aliyunm.musicplayer.popup

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.DisplayUtils
import com.aliyunm.common.utils.ScreenUtils
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.adapter.MusicListAdapter
import com.aliyunm.musicplayer.databinding.PopupMusicListBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel
import com.google.android.exoplayer2.Player

class MusicListPopup(activity: ComponentActivity) : BaseBottomPopup<PopupMusicListBinding>(activity) {

    private lateinit var viewModel : MusicViewModel

    override fun initData() {
        viewModel = CommonApplication.getApplication().getViewModel(MusicViewModel::class.java)
    }

    override fun initView() {

    }

    override fun getBinding(): PopupMusicListBinding {
        return PopupMusicListBinding.inflate(LayoutInflater.from(getActivity()), null, false)
    }

    override fun show(isDark : Boolean) {
        val height: Int = ScreenUtils.getScreenHeight(getActivity())
        super.show(LinearLayout.LayoutParams.MATCH_PARENT, height * 2 / 3, isDark)
    }
}