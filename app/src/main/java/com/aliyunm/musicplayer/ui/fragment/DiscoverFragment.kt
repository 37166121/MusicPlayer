package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.databinding.FragmentDiscoverBinding
import com.aliyunm.musicplayer.viewmodel.DiscoverViewModel

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, DiscoverViewModel>() {
    override fun setBinding(): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<DiscoverViewModel> {
        return DiscoverViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {

    }
}