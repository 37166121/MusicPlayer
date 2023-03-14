package com.aliyunm.musicplayer.ui.activity

import android.os.Bundle
import com.aliyunm.common.ui.BaseActivity
import com.aliyunm.musicplayer.databinding.ActivitySettingBinding
import com.aliyunm.musicplayer.viewmodel.SettingViewModel

class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {
    override fun setBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<SettingViewModel> {
        return SettingViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {

    }

}