package com.qatros.qtn_bina_murid.utils

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.qatros.qtn_bina_murid.R

fun ImageView.loadImageUser(url: String?, placeholder: Int = R.drawable.ic_user_default) {
    Glide.with(this)
        .load(url)
        .apply(
            RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(placeholder)
                .error(placeholder)
        )
        .into(this)
}

fun Activity.toast(message:String?){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}