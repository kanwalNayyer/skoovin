package com.kotlin.mvvm.boilerplate.data.repository

import android.app.Activity
import com.google.gson.Gson
import com.kotlin.mvvm.boilerplate.data.local.room.SongsEntity
import com.kotlin.mvvm.boilerplate.data.local.room.SongsJson
import com.kotlin.mvvm.boilerplate.di.qualifier.LocalData
import com.kotlin.mvvm.boilerplate.di.qualifier.RemoteData
import io.reactivex.Flowable
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Singleton
class SongsRepository @Inject constructor(
    @LocalData private val localDataSource: SongsDataSource) : SongsDataSource {

    private var cachedSong = listOf<SongsEntity>()

    private var cacheSongIsDirty = false

    private var activity : Activity? = null

    fun setActivity(activity: Activity)
    {
        this.activity = activity
    }

    override fun updateFavorite(isFavorite:Boolean,id:Int)
    {
        localDataSource.updateFavorite(isFavorite,id)
    }

    override fun updateAllFavorite(isfavorite: Boolean) {
        localDataSource.updateAllFavorite(isfavorite)
    }

    override fun getAllSongs(): Flowable<List<SongsEntity>> {
        if (cachedSong.isNotEmpty() && !cacheSongIsDirty) {
            return Flowable.just(cachedSong)
        }
        val remoteSong = getAndSaveRemoteSongs()

        return if (cacheSongIsDirty) remoteSong else {
            val localSong = getAndCacheLocalSong()
            Flowable.concat(localSong, remoteSong)
        }
    }


    override fun saveAllSongs(songs: List<SongsEntity>) {
        localDataSource.saveAllSongs(songs)
    }

    private fun getAndSaveRemoteSongs(): Flowable<List<SongsEntity>> {
        return getSongsList()
            .doOnNext { song ->
                localDataSource.saveAllSongs(song)
                cachedSong = song
            }.doOnComplete {
                cacheSongIsDirty = false
            }
    }

    private fun getAndCacheLocalSong(): Flowable<List<SongsEntity>> {
        return localDataSource.getAllSongs().doOnNext { songs -> cachedSong = songs }
    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = activity!!.getAssets().open("manifest.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getSongsList(): Flowable<List<SongsEntity>>
    {
        val song = Gson().fromJson(loadJSONFromAsset(), SongsJson::class.java)
       return Flowable.just(song.data)
    }
}