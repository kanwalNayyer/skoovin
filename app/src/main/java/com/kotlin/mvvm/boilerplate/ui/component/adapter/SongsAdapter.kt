package com.kotlin.mvvm.boilerplate.ui.component.adapter

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.kotlin.mvvm.boilerplate.R
import com.kotlin.mvvm.boilerplate.data.local.room.SongsEntity
import com.kotlin.mvvm.boilerplate.databinding.ItemSongBinding
import com.kotlin.mvvm.boilerplate.ui.main.home.HomeViewModel

/**
 * Created by cuongpm on 12/10/18.
 */

class SongsAdapter(
    private var songs: List<SongsEntity>,
    private val homeViewModel: HomeViewModel?
) : RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val binding = DataBindingUtil.inflate<ItemSongBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_song, parent, false
        )

        return SongsViewHolder(binding)
    }
    fun getDataSet(): List<SongsEntity>
    {
        return songs
    }


    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) =
        holder.bind(songs[position], object : SongsListener {
            override fun onSongSelected(songs: SongsEntity) {
                homeViewModel?.openSongs(songs)
            }
            override fun onFavoriteTab(songs: SongsEntity) {
                homeViewModel?.updateAllSongs(false).apply {
                    getDataSet().forEach {
                        it.isFavourite = false
                    }
                    if (songs.isFavourite)
                    {
                        songs.isFavourite = false
                        homeViewModel?.updateSongs(false,songs.id)
                    }else{
                        songs.isFavourite = true
                        homeViewModel?.updateSongs(true,songs.id)
                    }
                    notifyDataSetChanged()
                }


            }
        })

    class SongsViewHolder(private val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(songsEntity: SongsEntity, songListener: SongsListener) {
            with(binding)
            {
                song = songsEntity
                listener = songListener
                imageUrl = songsEntity.cover
                executePendingBindings()
            }
        }
    }

    fun setData(songs: List<SongsEntity>) {
        this.songs = songs
        notifyDataSetChanged()
    }

}

interface SongsListener {
    fun onSongSelected(songs: SongsEntity)
    fun onFavoriteTab(songs: SongsEntity)
}
