package com.example.app_chaski.utils

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.app_chaski.R
import com.google.android.material.snackbar.Snackbar

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun ImageView.loadImage(url: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    Glide.with(this.context)
        .load(url)
        .apply(options)
        .into(this)
}

fun ImageView.loadCircularImage(url: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .circleCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    Glide.with(this.context)
        .load(url)
        .apply(options)
        .into(this)
}

