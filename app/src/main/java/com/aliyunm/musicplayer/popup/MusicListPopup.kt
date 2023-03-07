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

    private lateinit var viewModel : MusicViewModel

    override fun initData() {
        viewModel = CommonApplication.getApplication().getViewModel(MusicViewModel::class.java)
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
        viewModel.position.observe(getActivity()) {
            viewBinding.rvMusicList.adapter?.apply {
                notifyItemChanged(viewModel.oldPosition)
                notifyItemChanged(viewModel.nowPosition)
            }
        }
        viewBinding.apply {
            tvMusicCount.text = "(${viewModel.musicItems.size})"

            ivClear.setOnClickListener {
                viewModel.musicItems.clear()
                viewModel.player.clearMediaItems()
                rvMusicList.adapter?.notifyDataSetChanged()
            }

            tvCycle.apply {
                setTypeIcon(viewModel.repeatMode, this)
                setOnClickListener {
                    viewModel.repeatMode = ++viewModel.repeatCount % (Player.REPEAT_MODE_ALL + 1)
                    setTypeIcon(viewModel.repeatMode, this)
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
                        viewModel.isFirst = false
                    }
                })
                adapter = MusicListAdapter(viewModel.musicItems) { position ->
                    viewModel.scroll(position)
                }.apply {
                    viewModel.musicItems.apply {
                        event.observe(getActivity()) {
                            tvMusicCount.text = "(${viewModel.musicItems.size})"
                            if (size == 0) {
                                dismiss()
                            } else {
                                notifyDataSetChanged()
                            }
                        }

                        removeAt.observe(getActivity()) { position ->
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, viewModel.musicItems.size)
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