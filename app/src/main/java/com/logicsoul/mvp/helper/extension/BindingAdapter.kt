package com.logicsoul.mvp.helper.extension


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.logicsoul.mvp.model.User
import com.logicsoul.mvp.view.feature.UsersAdapter

@BindingAdapter("bind:src")
fun setImageViewResource(view: ImageView, resId: Int) {
    view.setImageResource(resId)
}

@BindingAdapter("bind:background")
fun setBackgroundColor(view: RecyclerView, colorId: Int) {
    view.setBackgroundColor(view.context.resources.getColor(colorId))
}

@BindingAdapter("bind:adapter")
fun setAdapter(view: RecyclerView, baseAdapter: RecyclerView.Adapter<*>) {
    view.adapter = baseAdapter
}

@BindingAdapter("bind:items")
fun setItems(view: RecyclerView, items: List<User>?) {
    view.adapter = UsersAdapter().apply {
        itemList = items ?: emptyList()
        notifyDataSetChanged()
    }
}

// for multiple adapter items
//@BindingAdapter("app:items")
//fun <T> setItems(listView: RecyclerView, items: List<T>?) {
//    items?.let {
//        if (listView.adapter is UsersAdapter) {
//            (listView.adapter as UsersAdapter).itemList = items
//        }else if(){
//
//        }
//    }



    @BindingAdapter("bind:loadUrl")
    fun bindUrlImage(view: ImageView, url: String) {
        Glide.with(view)
            .load(url)
            .into(view)
    }