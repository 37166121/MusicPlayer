package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.databinding.FragmentMineBinding
import com.aliyunm.musicplayer.ui.activity.SettingActivity
import com.aliyunm.musicplayer.viewmodel.MineViewModel

class MineFragment : BaseFragment<FragmentMineBinding, MineViewModel>() {
    override fun setBinding(): FragmentMineBinding {
        return FragmentMineBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MineViewModel> {
        return MineViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        viewBinding.apply {
            ivSetting.setOnClickListener {
                SettingActivity.start(requireContext())
            }
        }
    }
}