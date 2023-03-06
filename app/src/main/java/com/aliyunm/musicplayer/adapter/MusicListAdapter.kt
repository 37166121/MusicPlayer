package com.aliyunm.musicplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.aliyunm.common.utils.AnimationUtils
import com.aliyunm.common.utils.AnimationUtils.translateAnimation
import com.aliyunm.musicplayer.LiveArrayList
import com.aliyunm.musicplayer.databinding.ItemMusicListBinding
import com.aliyunm.musicplayer.model.MusicModel

class MusicListAdapter(override val data : LiveArrayList<MusicModel>, private val callback : (playingPosition : Int) -> Unit = {}) : BaseAdapter<MusicModel, MusicListAdapter.MusicListViewHolder>(data) {

    private var oldPosition = 0

    class MusicListViewHolder(override val binding : ItemMusicListBinding) : BaseViewHolder<MusicModel, ItemMusicListBinding>(binding) {
        override fun bind(data: MusicModel, context: Context) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListViewHolder {
        setContext(parent.context)
        return MusicListViewHolder(ItemMusicListBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            // setIsRecyclable(false)
        }
    }

    override fun onBindViewHolder(holder: MusicListViewHolder, position: Int) {
        val item = data[position]
        holder.binding.tvMusicName.text = item.name
        holder.binding.tvMusicSinger.text = item.singer
        showPlaying(item.isPlaying, holder.binding.ivMusicPlaying)
        holder.binding.ivRemove.setOnClickListener {
            data.removeAt(position)
        }
        holder.itemView.setOnClickListener {
            callback(position)
            notifyItemChanged(oldPosition)
            showPlaying(item.isPlaying, holder.binding.ivMusicPlaying)
            oldPosition = position
        }
    }

    private fun showPlaying(isPlaying : Boolean, view : View) {
        if (isPlaying) {
            view.visibility = View.VISIBLE
            // val animation = translateAnimation(AnimationUtils.Translate.H, 0f, 1f)
            // view.startAnimation(animation)
            // view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
            // val animation = translateAnimation(AnimationUtils.Translate.H, 1f, 0f).apply {
            //     setAnimationListener(object : Animation.AnimationListener {
            //         override fun onAnimationStart(animation: Animation?) {
            //
            //         }
            //
            //         override fun onAnimationEnd(animation: Animation?) {
            //             view.visibility = View.GONE
            //         }
            //
            //         override fun onAnimationRepeat(animation: Animation?) {
            //
            //         }
            //     })
            // }
            // view.startAnimation(animation)
        }
    }
}