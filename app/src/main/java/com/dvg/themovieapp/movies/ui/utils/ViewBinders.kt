package com.dvg.themovieapp.movies.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dvg.themovieapp.R
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@BindingAdapter("srcUrl")
fun ImageView.bindSrcUrl(
    url: String?
) {
    url?.let {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(this)
    }
}

@BindingAdapter("imageList")
fun ImageCarousel.imageList(
    imageList: List<CarouselItem>?
) {
    imageList?.let {
        this.setData(it)
    }
}