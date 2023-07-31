package com.aliyunm.musicplayer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.aliyunm.common.ui.BaseActivity
import com.aliyunm.common.utils.SharedPreferencesUtils
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.databinding.ActivityMainBinding
import com.aliyunm.musicplayer.floatview.popup.MusicListPopup
import com.aliyunm.musicplayer.floatview.popup.PlayerPopup
import com.aliyunm.musicplayer.ui.fragment.PlayerBottomFragment
import com.aliyunm.musicplayer.viewmodel.MusicViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MusicViewModel>() {

    private lateinit var playerBottomFragment: PlayerBottomFragment

    override fun setBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override fun initData(savedInstanceState: Bundle?) {
        playerBottomFragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, "playerBottomFragment") as PlayerBottomFragment
        } else {
            PlayerBottomFragment()
        }
        viewModel.playerPopup = PlayerPopup(this)
        viewModel.musicListPopup = MusicListPopup(this)
    }

    override fun initView() {
        if (!playerBottomFragment.isAdded) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_player_bottom, playerBottomFragment)
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        supportFragmentManager.putFragment(outState, "playerBottomFragment", playerBottomFragment)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.player.release()
        SharedPreferencesUtils.putInt(SharedPreferencesUtils.POSITION, viewModel.nowPosition).commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val home = Intent(Intent.ACTION_MAIN).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                addCategory(Intent.CATEGORY_HOME)
            }
            startActivity(home)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}