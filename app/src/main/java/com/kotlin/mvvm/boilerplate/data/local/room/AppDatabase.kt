package com.kotlin.mvvm.boilerplate.data.local.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by cuongpm on 12/1/18.
 */

@Database(entities = [SongsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun songsDao(): SongsDao
}