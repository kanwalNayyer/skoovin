package com.kotlin.mvvm.boilerplate.data.repository

import com.kotlin.mvvm.boilerplate.data.local.room.SongsEntity
import io.reactivex.Flowable

/**
 * Created by cuongpm on 11/29/18.
 */

interface SongsDataSource {

    fun getAllSongs(): Flowable<List<SongsEntity>>
    fun saveAllSongs(songs: List<SongsEntity>)
    fun updateFavorite(isfavorite:Boolean,id:Int)
    fun updateAllFavorite(isfavorite:Boolean)
}