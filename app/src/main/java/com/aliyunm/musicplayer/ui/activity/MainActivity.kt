package com.aliyunm.musicplayer.ui.activity

import android.Manifest
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aliyunm.common.ui.BaseActivity
import com.aliyunm.common.utils.PermissionUtils
import com.aliyunm.common.utils.SharedPreferencesUtils
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.databinding.ActivityMainBinding
import com.aliyunm.musicplayer.popup.MusicListPopup
import com.aliyunm.musicplayer.popup.PlayerPopup
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
        viewModel.musicListPopup = MusicListPopup(this)

        viewModel.isPlaying.observe(this) {
            if (it) {
                viewModel.player.play()
            } else {
                viewModel.player.pause()
            }
        }
        viewModel.musicItems.apply {

            event.observe(this@MainActivity) {
                if (size == 0) {
                    viewModel.nowPosition = 0
                }
            }

            add.observe(this@MainActivity) {
                viewModel.player.addMediaItem(it!!.mediaItem)
            }

            addAll.observe(this@MainActivity) { musicList ->
                musicList.forEach {
                    viewModel.player.addMediaItem(it.mediaItem)
                }
            }

            remove.observe(this@MainActivity) {

            }

            removeAt.observe(this@MainActivity) { position ->
                viewModel.player.removeMediaItem(position)
            }

            removeAll.observe(this@MainActivity) {
                viewModel.player.clearMediaItems()
            }

            clear.observe(this@MainActivity) {
                viewModel.player.clearMediaItems()
            }
        }
        viewModel.nowPosition = SharedPreferencesUtils.getInt(SharedPreferencesUtils.POSITION, 0)
        PermissionUtils.requestPermission(this, Manifest.permission.RECORD_AUDIO, {
            // 权限被允许
            viewModel.isRecordAudio = true
            viewModel.playerPopup = PlayerPopup(this)
        }, {
            // 权限被拒绝
        })
    }

    override fun initView() {
        val navController = findNavController(R.id.nav_host_fragment_activity_nav)
        viewBinding.navView.apply {
            itemIconTintList = null
            setupWithNavController(navController)
        }

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
}