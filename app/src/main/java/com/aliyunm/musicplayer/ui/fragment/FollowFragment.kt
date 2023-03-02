package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.databinding.FragmentFollowBinding
import com.aliyunm.musicplayer.viewmodel.FollowViewModel

class FollowFragment : BaseFragment<FragmentFollowBinding, FollowViewModel>() {
    override fun setBinding(): FragmentFollowBinding {
        return FragmentFollowBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<FollowViewModel> {
        return FollowViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {

    }
}