package com.aliyunm.musicplayer.popup

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.DisplayUtils
import com.aliyunm.common.utils.ScreenUtils
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.adapter.MusicListAdapter
import com.aliyunm.musicplayer.databinding.PopupMusicListBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel
import com.google.android.exoplayer2.Player

class MusicListPopup(activity: ComponentActivity) : BaseBottomPopup<PopupMusicListBinding>(activity) {

    private lateinit var viewModel : MusicViewModel
    private lateinit var musicListAdapter : MusicListAdapter

    override fun initData() {
        viewModel = CommonApplication.getApplication().getViewModel(MusicViewModel::class.java)
        viewModel.repeatMode.observe(getActivity()) {
            setTypeIcon(it, viewBinding.tvCycle)
        }
        musicListAdapter = MusicListAdapter(viewModel.musicItems) {
            viewModel.nowPosition = it
        }
    }

    override fun initView() {
        viewBinding.apply {
            rvMusicList.apply {
                adapter = musicListAdapter
            }
            tvMusicCount.text = "(${viewModel.musicItems.size})"

            ivClear.setOnClickListener {
                viewModel.musicItems.clear()
                rvMusicList.adapter?.notifyDataSetChanged()
            }

            tvCycle.apply {
                setTypeIcon(viewModel.repeatMode.value ?: -1, this)
                setOnClickListener {
                    viewModel.switchMode()
                }
            }
        }
    }

    override fun getBinding(): PopupMusicListBinding {
        return PopupMusicListBinding.inflate(LayoutInflater.from(getActivity()), null, false)
    }

    override fun show(isDark : Boolean) {
        val height: Int = ScreenUtils.getScreenHeight(getActivity())
        super.show(LinearLayout.LayoutParams.MATCH_PARENT, height * 2 / 3, isDark)
    }

    /**
     * 切换播放模式图标
     * @param mode Int
     * @param view TextView
     */
    private fun setTypeIcon(mode : Int, view : TextView) {
        val playMode = this@MusicListPopup.getContext().resources.getStringArray(R.array.play_mode)
        val typeIcon : Drawable? = when(mode) {
            Player.REPEAT_MODE_OFF -> {
                this@MusicListPopup.getContext().getDrawable(R.drawable.ic_cycle_random)
            }
            Player.REPEAT_MODE_ONE -> {
                this@MusicListPopup.getContext().getDrawable(R.drawable.ic_cycle_single)
            }
            Player.REPEAT_MODE_ALL -> {
                this@MusicListPopup.getContext().getDrawable(R.drawable.ic_cycle_list)
            }
            else -> {
                this@MusicListPopup.getContext().getDrawable(R.drawable.ic_cycle_list)
            }
        }
        typeIcon?.bounds = Rect(0, DisplayUtils.dp2px(1f), DisplayUtils.dp2px(15f), DisplayUtils.dp2px(15f))
        view.apply {
            text = playMode[mode]
            setCompoundDrawables(typeIcon, null, null, null)
        }
    }
}