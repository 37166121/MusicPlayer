package com.aliyunm.musicplayer.popup

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.DisplayUtils
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.adapter.MusicListAdapter
import com.aliyunm.musicplayer.databinding.PopupMusicListBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel
import com.google.android.exoplayer2.Player

class MusicListPopup(activity: ComponentActivity) : BaseBottomPopup<PopupMusicListBinding>(activity) {

    private lateinit var musicViewModel : MusicViewModel

    override fun initData() {
        musicViewModel = CommonApplication.getApplication().getViewModel(MusicViewModel::class.java)
    }

    private fun setTypeIcon(type : Int, view : TextView) {
        val playMode = this@MusicListPopup.getContext().resources.getStringArray(R.array.play_mode)
        val typeIcon : Drawable = when(type) {
            Player.REPEAT_MODE_OFF -> {
                this@MusicListPopup.getContext().getDrawable(R.drawable.ic_cycle_random)!!
            }
            Player.REPEAT_MODE_ONE -> {
                this@MusicListPopup.getContext().getDrawable(R.drawable.ic_cycle_single)!!
            }
            Player.REPEAT_MODE_ALL -> {
                this@MusicListPopup.getContext().getDrawable(R.drawable.ic_cycle_list)!!
            }
            else -> {
                this@MusicListPopup.getContext().getDrawable(R.drawable.ic_cycle_list)!!
            }
        }
        typeIcon.bounds = Rect(0, DisplayUtils.dp2px(1f), DisplayUtils.dp2px(15f), DisplayUtils.dp2px(15f))
        view.apply {
            text = playMode[type]
            setCompoundDrawables(typeIcon, null, null, null)
        }
    }

    override fun initView() {
        viewBinding.apply {
            tvMusicCount.text = "(${musicViewModel.musicItems.size})"

            ivClear.setOnClickListener {
                musicViewModel.musicItems.clear()
                rvMusicList.adapter?.notifyDataSetChanged()
            }

            tvCycle.apply {
                setTypeIcon(musicViewModel.repeatMode, this)
                setOnClickListener {
                    musicViewModel.repeatMode = ++musicViewModel.repeatCount % (Player.REPEAT_MODE_ALL + 1)
                    setTypeIcon(musicViewModel.repeatMode, this)
                }
            }

            rvMusicList.apply {
                adapter = MusicListAdapter(musicViewModel.musicItems) { playingPosition ->
                    musicViewModel.position = playingPosition
                }.apply {
                    musicViewModel.musicItems.apply {
                        event.observe(getActivity()) {
                            tvMusicCount.text = "(${musicViewModel.musicItems.size})"
                        }

                        add.observe(getActivity()) {

                        }

                        addAll.observe(getActivity()) {

                        }

                        remove.observe(getActivity()) {

                        }

                        removeAt.observe(getActivity()) { position ->
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, musicViewModel.musicItems.size)
                        }

                        removeAll.observe(getActivity()) {

                        }

                        clear.observe(getActivity()) {
                            if (it) {
                                dismiss()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getBinding(): PopupMusicListBinding {
        return PopupMusicListBinding.inflate(LayoutInflater.from(getActivity()), null, false)
    }

    override fun show() {
        val height: Int = getActivity().windowManager.defaultDisplay.height
        super.show(LinearLayout.LayoutParams.MATCH_PARENT, height * 2 / 3)
    }
}