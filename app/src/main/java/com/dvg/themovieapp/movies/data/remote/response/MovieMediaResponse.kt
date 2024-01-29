package com.dvg.themovieapp.movies.data.remote.response

import com.dvg.themovieapp.movies.models.Backdrop
import com.dvg.themovieapp.movies.models.Poster
import com.dvg.themovieapp.movies.utils.Constants
import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@JsonClass(generateAdapter = true)
data class MovieMediaResponse(
    val id: Int,
    val backdrops: List<Backdrop>,
    val posters: List<Poster>
){
    fun getPosterImages(): List<CarouselItem> = backdrops.map {
        CarouselItem(
            imageUrl = "${Constants.IMAGE_BASE_URL}${it.file_path}"
        )
    }
}