package com.brx.mobileapp.util.extension

import android.content.Context
import android.widget.ImageView
import com.brx.mobileapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadRemoteImage(context: Context?, url: String?) {
    context?.let {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(this)
    }
}