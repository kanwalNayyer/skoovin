package com.kotlin.mvvm.boilerplate.di.module

import com.kotlin.mvvm.boilerplate.di.ActivityScoped
import com.kotlin.mvvm.boilerplate.ui.main.home.HomeModule
import com.kotlin.mvvm.boilerplate.ui.main.home.MainActivity
import com.kotlin.mvvm.boilerplate.ui.main.home.TrackDetailActivity
import com.kotlin.mvvm.boilerplate.ui.main.home.TrackModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by cuongpm on 11/29/18.
 */

@Module
internal abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TrackModule::class])
    internal abstract fun bindTrackDetailActivity(): TrackDetailActivity

}