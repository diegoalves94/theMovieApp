package com.dvg.themovieapp.movies.data.remote.response

import com.dvg.themovieapp.movies.data.local.entities.Backdrop
import com.dvg.themovieapp.movies.data.models.Poster
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class MovieMediaResponse(
    val id: Int,
    val backdrops: List<Backdrop>,
    val posters: List<Poster>
)