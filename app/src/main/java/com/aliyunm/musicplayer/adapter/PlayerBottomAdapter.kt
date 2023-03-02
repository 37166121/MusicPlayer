package com.aliyunm.musicplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aliyunm.musicplayer.databinding.ItemMusicPlayerBottomBinding
import com.aliyunm.musicplayer.model.MusicModel
import com.aliyunm.common.setImage

class PlayerBottomAdapter(override val data : ArrayList<MusicModel>) : BaseAdapter<MusicModel, PlayerBottomAdapter.PlayerBottomViewHolder>(data) {

    class PlayerBottomViewHolder(override val binding: ItemMusicPlayerBottomBinding) : BaseViewHolder<MusicModel, ItemMusicPlayerBottomBinding>(binding) {
        override fun bind(data: MusicModel, context : Context) {
            binding.ivMusicCover.setImage(data.coverPath)
            binding.tvMusicName.text = data.name
            binding.tvMusicSinger.text = data.singer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerBottomViewHolder {
        setContext(parent.context)
        return PlayerBottomViewHolder(ItemMusicPlayerBottomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PlayerBottomViewHolder, position: Int) {
        holder.bind(data[position], getContext())
    }
}