package com.dvg.themovieapp.movies.data.local.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dvg.themovieapp.movies.utils.Constants
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@Entity
class MovieMedia() {

    @PrimaryKey
    var movieId: Int? = null

    @Ignore
    var backdrops: List<Backdrop>? = null

    @Ignore
    constructor(
        movieId: Int?,
        backdrops: List<Backdrop>?
    ) : this() {
        this.movieId = movieId
        this.backdrops = backdrops
    }

    fun getPosterImages(): List<CarouselItem> = backdrops!!.map {
        CarouselItem(
            imageUrl = "${Constants.IMAGE_BASE_URL}${it.file_path}"
        )
    }
}
