package com.dvg.themovieapp.movies.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dvg.themovieapp.movies.utils.Constants
import com.squareup.moshi.JsonClass
@Entity
@JsonClass(generateAdapter = true)
data class Movie(
    @PrimaryKey
    var id: Int? = null,
    var title: String? = null,
    var overview: String? = null,
    var backdrop_path: String? = null
) {
    fun getMovieId() = id
    fun getBannerImagePath() = "${Constants.IMAGE_BASE_URL}${backdrop_path}"

}