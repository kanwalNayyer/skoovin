package com.kotlin.mvvm.boilerplate.data.local.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kotlin.mvvm.boilerplate.util.RoomConverter


/**
 * Created by cuongpm on 12/1/18.
 */

@Entity(tableName = "Songs")
@TypeConverters(RoomConverter::class)
data class SongsEntity constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title: String = "",

    @ColumnInfo(name = "audio")
    @SerializedName("audio")
    @Expose
    var audio: String = "",

    @ColumnInfo(name = "cover")
    @SerializedName("cover")
    @Expose
    var cover: String = "",

    @ColumnInfo(name = "totalDurationMs")
    @SerializedName("totalDurationMs")
    @Expose
    var totalDurationMs: String = "",

    @ColumnInfo(name = "rating")
    @SerializedName("rating")
    @Expose
    var rating: String = "3",


    @ColumnInfo(name = "isFavourite")
    @SerializedName("isFavourite")
    @Expose
    var isFavourite: Boolean = false
)
