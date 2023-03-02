package com.aliyunm.musicplayer.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>(open val data : ArrayList<T>) : RecyclerView.Adapter<VH>() {

    private lateinit var mContext : Context

    abstract class BaseViewHolder<T, VB : ViewBinding>(open val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(data : T, context : Context)
    }

    fun setContext(context : Context) {
        mContext = context
    }

    fun getContext(): Context {
        return mContext
    }

    override fun getItemCount(): Int {
        return data.size
    }
}