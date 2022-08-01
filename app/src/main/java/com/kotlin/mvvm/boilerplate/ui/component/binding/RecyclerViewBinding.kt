package com.kotlin.mvvm.boilerplate.ui.component.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import com.kotlin.mvvm.boilerplate.data.local.room.SongsEntity
import com.kotlin.mvvm.boilerplate.ui.component.adapter.SongsAdapter

/**
 * Created by cuongpm on 12/13/18.
 */

object RecyclerViewBinding {

    @BindingAdapter("app:addVerticalItemDecoration")
    @JvmStatic
    fun RecyclerView.addVerticalItemDecoration(isVertical: Boolean) {
        addItemDecoration(DividerItemDecoration(context, if (isVertical) 1 else 0))
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun setListSongs(recyclerView: RecyclerView, items: List<SongsEntity>) {
        with(recyclerView.adapter as SongsAdapter?) {
            this?.setData(items)
        }
    }

}