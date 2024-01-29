package com.dvg.themovieapp.movies.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailResponse(
    val id: Int?,
    val title: String?,
    val original_title: String?,
    val overview: String?,
    val backdrop_path: String?,
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Double?
)
