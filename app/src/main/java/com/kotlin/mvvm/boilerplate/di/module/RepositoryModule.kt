package com.kotlin.mvvm.boilerplate.di.module

import com.kotlin.mvvm.boilerplate.data.local.SongsLocalDataSource
import com.kotlin.mvvm.boilerplate.data.repository.SongsDataSource
import com.kotlin.mvvm.boilerplate.di.qualifier.LocalData
import com.kotlin.mvvm.boilerplate.di.qualifier.RemoteData
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    @LocalData
    abstract fun bindNewsLocalDataSource(songsLocalDataSource: SongsLocalDataSource): SongsDataSource

}