package com.kotlin.mvvm.boilerplate.data.local.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by cuongpm on 12/1/18.
 */

@Dao
interface SongsDao {

    @Query("SELECT * FROM Songs")
    fun getAllSongs(): Maybe<List<SongsEntity>>

    @Query("SELECT * FROM Songs WHERE id = :songId")
    fun getSongById(songId: Int): Flowable<SongsEntity>

    @Query("UPDATE Songs SET isFavourite=:isFavorite WHERE id = :id")
    fun updateFavorite(isFavorite: Boolean?, id: Int)

    @Query("UPDATE Songs SET isFavourite=:isFavorite")
    fun updateAllFavorite(isFavorite: Boolean?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSong(songs: SongsEntity)
}