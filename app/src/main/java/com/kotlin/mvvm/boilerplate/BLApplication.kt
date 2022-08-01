package com.kotlin.mvvm.boilerplate

import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.kotlin.mvvm.boilerplate.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by cuongpm on 11/29/18.
 */

open class BLApplication : DaggerApplication() {

    private lateinit var androidInjector: AndroidInjector<out DaggerApplication>

    override fun onCreate() {
        super.onCreate()

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)

        androidInjector = DaggerAppComponent.builder().application(this).build()
    }

    public override fun applicationInjector(): AndroidInjector<out DaggerApplication> = androidInjector

}