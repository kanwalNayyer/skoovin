package com.kotlin.mvvm.boilerplate.ui.main.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.kotlin.mvvm.boilerplate.databinding.FragmentHomeBinding
import com.kotlin.mvvm.boilerplate.di.ActivityScoped
import com.kotlin.mvvm.boilerplate.ui.component.adapter.SongsAdapter
import com.kotlin.mvvm.boilerplate.ui.main.base.BaseFragment

import javax.inject.Inject

/**
 * Created by cuongpm on 11/29/18.
 */

@ActivityScoped
class HomeFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var dataBinding: FragmentHomeBinding

    private lateinit var songAdapter: SongsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.setActivity(requireActivity())
        songAdapter = SongsAdapter(ArrayList(0), homeViewModel)

        dataBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            this.viewModel = homeViewModel
            this.adapter = songAdapter
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.start()
        handleUIEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.stop()
    }

    private fun handleUIEvent() {
        homeViewModel.onSongOpenEvent.observe(this, Observer { song ->
            song?.let {
//                val intent = Intent(context, CommentActivity::class.java)
//                intent.putExtra(NEWS_DATA, Gson().toJson(it))
//                startActivity(intent)
            }
        })
    }
}