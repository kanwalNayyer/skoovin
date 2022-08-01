package com.kotlin.mvvm.boilerplate.data.local

import com.kotlin.mvvm.boilerplate.data.local.room.SongsDao
import com.kotlin.mvvm.boilerplate.data.local.room.SongsEntity
import com.kotlin.mvvm.boilerplate.data.repository.SongsDataSource
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Singleton
class SongsLocalDataSource @Inject constructor(
    private val songsDao: SongsDao
) : SongsDataSource {

    override fun getAllSongs(): Flowable<List<SongsEntity>> {
        return songsDao.getAllSongs().toFlowable()
    }


    override fun saveAllSongs(songs: List<SongsEntity>) {
        songs.map { songsDao.insertSong(it) }
    }

    override fun updateFavorite(isfavorite: Boolean,id:Int) {
        Thread {
            //Do your database´s operations here
            songsDao.updateFavorite(isfavorite,id)
        }.start()

    }

    override fun updateAllFavorite(isfavorite: Boolean) {
        Thread {
            //Do your database´s operations here
            songsDao.updateAllFavorite(isfavorite)
        }.start()
    }

}