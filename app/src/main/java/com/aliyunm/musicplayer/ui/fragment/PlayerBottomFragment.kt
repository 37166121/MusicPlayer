package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.adapter.PlayerBottomAdapter
import com.aliyunm.musicplayer.databinding.FragmentPlayerBottomBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel

class PlayerBottomFragment : BaseFragment<FragmentPlayerBottomBinding, MusicViewModel>() {

    override fun setBinding(): FragmentPlayerBottomBinding {
        return FragmentPlayerBottomBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {

    }
}