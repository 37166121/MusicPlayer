package com.aliyunm.musicplayer.popup

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.aliyunm.common.CommonApplication
import com.aliyunm.common.utils.DisplayUtils
import com.aliyunm.common.utils.ScreenUtils
import com.aliyunm.musicplayer.R
import com.aliyunm.musicplayer.adapter.MusicListAdapter
import com.aliyunm.musicplayer.databinding.PopupMusicListBinding
import com.aliyunm.musicplayer.viewmodel.MusicViewModel
import com.google.android.exoplayer2.Player

class MusicListPopup(activity: ComponentActivity) : BaseBottomPopup<PopupMusicListBinding>(activity) {

    private lateinit var musicViewModel : MusicViewModel

    override fun initData() {
        musicViewModel = CommonApplication.getApplication().getViewModel(MusicViewModel::class.java)
        musicViewModel.position.observe(getActivity()) {
            viewBinding.rvMusicList.adapter?.notifyDataSetChanged()
        }
    }

    private fun setTypeIcon(type : Int, view : TextView) {
        val playMode = this@MusicListPopup.getContext().resources.getStringArray(R.array.play_mode)
        val drawable : Int = when(type) {
            Player.REPEAT_MODE_OFF -> {
                R.drawable.ic_cycle_random
            }
            Player.REPEAT_MODE_ONE -> {
                R.drawable.ic_cycle_single
            }
            Player.REPEAT_MODE_ALL -> {
                R.drawable.ic_cycle_list
            }
            else -> {
                R.drawable.ic_cycle_list
            }
        }
        val typeIcon : Drawable = getContext().getDrawable(drawable)!!
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
                musicViewModel.player.clearMediaItems()
                rvMusicList.adapter?.notifyDataSetChanged()
            }

            tvCycle.apply {
                setTypeIcon(musicViewModel.repeatMode, this)
                setOnClickListener {
                    musicViewModel.repeatMode = ++musicViewModel.repeatCount % (Player.REPEAT_MODE_ALL + 1)
                    musicViewModel.player.repeatMode = musicViewModel.repeatMode
                    setTypeIcon(musicViewModel.repeatMode, this)
                }
            }

            rvMusicList.apply {
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        val isTop = canScrollVertically(-1)
                        if (!isTop) {
                            // setOnTouchListener(onTouchListener)
                        } else {
                            // setOnTouchListener(nullOnTouchListener)
                        }
                        super.onScrollStateChanged(recyclerView, newState)
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                    }
                })
                adapter = MusicListAdapter(musicViewModel.musicItems) { playingPosition ->
                    if (musicViewModel.nowPosition != playingPosition) {
                        musicViewModel.nowPosition = playingPosition
                    }
                }.apply {
                    musicViewModel.musicItems.apply {
                        event.observe(getActivity()) {
                            tvMusicCount.text = "(${musicViewModel.musicItems.size})"
                            if (size == 0) {
                                dismiss()
                            } else {
                                notifyDataSetChanged()
                            }
                        }

                        removeAt.observe(getActivity()) { position ->
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, musicViewModel.musicItems.size)
                        }
                    }
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
}