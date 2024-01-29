package com.dvg.themovieapp.movies.models

import com.dvg.themovieapp.movies.utils.Constants
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val release_date: String?,
    val vote_average: Double?,
    val backdrop_path: String?,
    val poster_path: String?
) {
    override fun toString(): String {
        return title!!
    }
    fun getMovieId() = id
    fun getBannerImagePath() = "${Constants.IMAGE_BASE_URL}${backdrop_path}"
    fun getPosterImagePath() = "${Constants.IMAGE_BASE_URL}${poster_path}"
    fun getVoteAverage() = "✮ ${vote_average?.toInt()}"

}