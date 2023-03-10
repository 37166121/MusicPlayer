package com.aliyunm.musicplayer.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.aliyunm.common.setImage
import com.aliyunm.common.utils.GlideUtils
import com.aliyunm.musicplayer.databinding.ItemMusicPlayerBottomBinding
import com.aliyunm.musicplayer.model.MusicModel

class PlayerBottomAdapter(override val data : ArrayList<MusicModel>, val clickCallback : () -> Unit = {}) : BaseAdapter<MusicModel, PlayerBottomAdapter.PlayerBottomViewHolder>(data) {

    private var operatingAnim: ObjectAnimator = ObjectAnimator()

    class PlayerBottomViewHolder(override val binding: ItemMusicPlayerBottomBinding) : BaseViewHolder<MusicModel, ItemMusicPlayerBottomBinding>(binding) {
        override fun bind(data: MusicModel, context : Context) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerBottomViewHolder {
        setContext(parent.context)
        return PlayerBottomViewHolder(ItemMusicPlayerBottomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    private lateinit var binding: ItemMusicPlayerBottomBinding

    override fun onBindViewHolder(holder: PlayerBottomViewHolder, position: Int) {
        // holder.bind(data[position], getContext())
        binding = holder.binding
        val item = data[position]
        holder.binding.apply {
            ivMusicCover.setImage(path = item.coverPath, type = GlideUtils.RoundedCorners, radian = 999)
            tvMusicName.text = item.name
            tvMusicSinger.text = item.singer
            startAnimation(item)
            root.setOnClickListener {
                clickCallback()
            }
        }
    }

    private fun startAnimation(item : MusicModel) {
        binding.apply {
            operatingAnim = ObjectAnimator.ofFloat(ivMusicCover, "rotation", 0f, 359f).apply {
                duration = 15 * 1000
                repeatCount = -1
                interpolator = LinearInterpolator()
            }
            if (item.isPlaying) {
                operatingAnim.start()
            } else {
                operatingAnim.cancel()
            }
        }
    }

    fun pause() {
        if (operatingAnim.isStarted) {
            operatingAnim.pause()
        }
    }

    fun resume() {
        if (operatingAnim.isStarted) {
            operatingAnim.resume()
        } else {
            operatingAnim.start()
        }
    }
}