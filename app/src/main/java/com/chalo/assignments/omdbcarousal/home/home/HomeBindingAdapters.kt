package com.chalo.assignments.omdbcarousal.home.home

import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.chalo.assignments.omdbcarousal.home.di.modules.GlideApp
import com.google.android.material.imageview.ShapeableImageView


object HomeBindingAdapters {

    @BindingAdapter("coverImage")
    @JvmStatic
    fun coverImage(imageView: ShapeableImageView, url: String) {
        GlideApp
            .with(imageView)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .transition(withCrossFade())
            .into(imageView)
    }

}