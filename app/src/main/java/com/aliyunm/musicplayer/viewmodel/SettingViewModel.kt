package com.aliyunm.musicplayer.viewmodel

import androidx.lifecycle.ViewModel
import com.aliyunm.musicplayer.ui.fragment.HeadFragment

class SettingViewModel : ViewModel() {
    val head = HeadFragment.newInstance("设置")
}