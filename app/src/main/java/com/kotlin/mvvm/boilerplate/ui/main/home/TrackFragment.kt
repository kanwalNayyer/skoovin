package com.kotlin.mvvm.boilerplate.ui.main.home

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.google.gson.Gson
import com.kotlin.mvvm.boilerplate.data.local.room.SongsEntity
import com.kotlin.mvvm.boilerplate.databinding.ActivityTrackDetailBinding
import com.kotlin.mvvm.boilerplate.databinding.FragmentHomeBinding
import com.kotlin.mvvm.boilerplate.di.ActivityScoped
import com.kotlin.mvvm.boilerplate.ui.component.adapter.SongsAdapter
import com.kotlin.mvvm.boilerplate.ui.main.base.BaseFragment
import java.util.concurrent.TimeUnit

import javax.inject.Inject

/**
 * Created by cuongpm on 11/29/18.
 */

@ActivityScoped
class TrackFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel
    companion object {
        const val SONG_DATA = "Song"
    }

    private lateinit var dataBinding: ActivityTrackDetailBinding
    private var mMediaPlayer: MediaPlayer? = null
    private var seekLength: Int = 0
    private var currentSong:SongsEntity? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.setActivity(requireActivity())
        dataBinding = ActivityTrackDetailBinding.inflate(inflater, container, false).apply {
            this.viewModel = homeViewModel
        }

       // currentSong = homeViewModel.getCurrentSong()
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentSong = arguments?.getString(SONG_DATA)?.let {
            Gson().fromJson(it, SongsEntity::class.java)
        }

        handleUIEvent()
        Toast.makeText(context,currentSong?.title,Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.stop()
    }

    private fun handleUIEvent() {
        mMediaPlayer = MediaPlayer()

        dataBinding.btnPlay.setOnClickListener {
            // your code to perform when the user clicks on the button
            playSong()
        }
    }

    private fun playSong() {

        if (!mMediaPlayer!!.isPlaying) {
            mMediaPlayer?.reset()
            mMediaPlayer?.setDataSource("https://raw.githubusercontent.com/Learnfield-GmbH/CodingChallange/master/shared/simple%20audio%20player/data/Oceansound.mp3")
            mMediaPlayer?.prepare()
            mMediaPlayer?.seekTo(seekLength)
            mMediaPlayer?.start()

//            binding.ibPlay.setImageDrawable(
//                ContextCompat.getDrawable(
//                    activity?.applicationContext!!,
//                    R.drawable.ic_pause
//                )
//            )
            updateSeekBar()
        } else {

            mMediaPlayer?.pause()
            seekLength = mMediaPlayer!!.currentPosition
//            binding.ibPlay.setImageDrawable(
//                ContextCompat.getDrawable(
//                    activity?.applicationContext!!,
//                    R.drawable.ic_play
//                )
//            )
        }
    }
    private fun updateSeekBar() {
        if (mMediaPlayer != null) {
            dataBinding.tvCurrentTime.text =
                durationConverter(mMediaPlayer!!.currentPosition.toLong())
        }
        seekBarSetUp()
        Handler().postDelayed(runnable, 50)
    }

    var runnable = Runnable { updateSeekBar() }

    private fun seekBarSetUp() {

        if (mMediaPlayer != null) {
            dataBinding.seekBar.progress = mMediaPlayer!!.currentPosition
            dataBinding.seekBar.max = mMediaPlayer!!.duration
        }
        dataBinding.seekBar.setOnSeekBarChangeListener(@SuppressLint("AppCompatCustomView")
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    mMediaPlayer!!.seekTo(progress)
                    dataBinding.tvCurrentTime.text = durationConverter(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (mMediaPlayer != null && mMediaPlayer!!.isPlaying) {
                    if (seekBar != null) {
                        mMediaPlayer!!.seekTo(seekBar.progress)
                    }
                }
            }
        })
    }

    private fun clearMediaPlayer(){
        if (mMediaPlayer != null) {
            if (mMediaPlayer!!.isPlaying) {
                mMediaPlayer!!.stop()
            }
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearMediaPlayer()
    }
    fun durationConverter(duration: Long): String {

        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(duration),
            TimeUnit.MILLISECONDS.toSeconds(duration) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        )
    }
}