package com.kotlin.mvvm.boilerplate.ui.main.home

import android.app.Activity
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import com.google.gson.Gson
import com.kotlin.mvvm.boilerplate.data.local.room.SongsEntity
import com.kotlin.mvvm.boilerplate.data.local.room.SongsJson
import com.kotlin.mvvm.boilerplate.data.repository.SongsRepository
import com.kotlin.mvvm.boilerplate.ui.main.base.BaseViewModel
import com.kotlin.mvvm.boilerplate.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

/**
 * Created by cuongpm on 11/29/18.
 */

class HomeViewModel @Inject constructor(
    private val songRepository: SongsRepository
) : BaseViewModel() {

    val isRefreshing = ObservableBoolean(false)
    val items: ObservableList<SongsEntity> = ObservableArrayList()
    val onSongOpenEvent = SingleLiveEvent<SongsEntity>()
    private var activity : Activity? = null

    private var disposable: Disposable? = null

    fun setActivity(activity: Activity)
    {
        this.activity = activity
    }

    override fun start() {
        getAllSongs()
    }

    override fun stop() {
        disposable?.let { if (!it.isDisposed) it.dispose() }
    }

    fun openSongs(songs: SongsEntity) {
        onSongOpenEvent.setValue(songs)
    }

    fun updateSongs(isFavorite:Boolean,id:Int) {
       songRepository.updateFavorite(isFavorite,id)
    }

    fun updateAllSongs(isFavorite:Boolean) {
        songRepository.updateAllFavorite(isFavorite)
    }

    private fun getAllSongs() {
        activity?.let { songRepository.setActivity(it) }
        disposable = songRepository.getAllSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isRefreshing.set(true) }
            .doAfterTerminate { isRefreshing.set(false) }
            .subscribe({ news ->
                with(items) {
                    clear()
                    addAll(news)
                }
            }, { error ->
                error.printStackTrace()
            })
    }



}