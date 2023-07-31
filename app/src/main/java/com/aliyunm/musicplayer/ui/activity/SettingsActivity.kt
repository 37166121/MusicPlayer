package com.aliyunm.musicplayer.ui.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aliyunm.common.ui.BaseActivity
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.databinding.SettingsActivityBinding
import com.aliyunm.musicplayer.viewmodel.SettingViewModel
import com.aliyunm.preference.PreferenceFragmentCompat

class SettingsActivity : BaseActivity<SettingsActivityBinding, SettingViewModel>() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SettingsActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun setBinding() : SettingsActivityBinding {
        return SettingsActivityBinding.inflate(layoutInflater)
    }

    override fun setViewModel() : Class<SettingViewModel> {
        return SettingViewModel::class.java
    }

    override fun initData(savedInstanceState : Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initView() {

    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState : Bundle?, rootKey : String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}