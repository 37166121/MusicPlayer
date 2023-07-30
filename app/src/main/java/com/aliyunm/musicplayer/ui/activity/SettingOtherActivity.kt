package com.aliyunm.musicplayer.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.aliyunm.common.ui.BaseActivity
import com.aliyunm.common.utils.SharedPreferencesUtils
import com.aliyunm.common.utils.SharedPreferencesUtils.ACCESSTOKEN
import com.aliyunm.common.utils.SharedPreferencesUtils.APPID
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.databinding.ActivitySettingBinding
import com.aliyunm.musicplayer.ui.fragment.HeadFragment
import com.aliyunm.musicplayer.viewmodel.SettingViewModel

class SettingOtherActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {

    private lateinit var headFragment : HeadFragment

    override fun setBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<SettingViewModel> {
        return SettingViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {
        headFragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, "headFragment") as HeadFragment
        } else {
            HeadFragment.newInstance("设置")
        }
    }

    override fun initView() {

        if (!headFragment.isAdded) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_head, headFragment)
                .commit()
        }

        viewBinding.apply {

            etAccessToken.setText(SharedPreferencesUtils.getString(ACCESSTOKEN, ""))
            etAppId.setText(SharedPreferencesUtils.getString(APPID, ""))

            btnSave.setOnClickListener {
                save()
            }
            toDeveloperMusic.setOnClickListener {
                val uri: Uri = Uri.parse("https://developer.music.163.com/st/developer/")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }

    private fun save() {
        val accessToken = viewBinding.etAccessToken.text.toString()
        val appId = viewBinding.etAppId.text.toString()
        SharedPreferencesUtils.putString(ACCESSTOKEN, accessToken)
            .putString(APPID, appId)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        supportFragmentManager.putFragment(outState, "headFragment", headFragment)
        super.onSaveInstanceState(outState)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, SettingOtherActivity::class.java)
            context.startActivity(starter)
        }
    }

}