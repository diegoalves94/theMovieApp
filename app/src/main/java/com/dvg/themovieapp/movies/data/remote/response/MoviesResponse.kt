package com.dvg.themovieapp.movies.data.remote.response

import com.dvg.themovieapp.movies.data.local.entities.Movie
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    val page: Int,
    val results: List<Movie>?,
    val total_pages: Int,
    val total_results: Int
)