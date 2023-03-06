package com.aliyunm.musicplayer.ui.activity

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aliyunm.common.ui.BaseActivity
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.databinding.ActivityMainBinding
import com.aliyunm.musicplayer.popup.MusicListPopup
import com.aliyunm.musicplayer.popup.PlayerPopup
import com.aliyunm.musicplayer.ui.fragment.PlayerBottomFragment
import com.aliyunm.musicplayer.viewmodel.MusicViewModel
import com.google.android.exoplayer2.ExoPlayer

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
        viewModel.player = ExoPlayer.Builder(this).build()
        viewModel.playerControlView = viewBinding.playerController
        viewModel.musicListPopup = MusicListPopup(this)
        viewModel.playerPopup = PlayerPopup(this)
        viewModel.musicItems.apply {

            forEach {
                viewModel.player.addMediaItem(it.mediaItem)
            }

            viewModel.player.apply {
                repeatMode = viewModel.repeatMode
                prepare()
            }

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

}