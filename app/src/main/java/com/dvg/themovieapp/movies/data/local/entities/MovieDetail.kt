package com.dvg.themovieapp.movies.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dvg.themovieapp.movies.utils.Constants
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class MovieDetail (
    @PrimaryKey
    var id: Int,
    var title: String? = null,
    var overview: String? = null,
    var release_date: String? = null,
    var vote_average: Double? = null,
    var poster_path: String? = null
){
    fun getPosterImagePath() = "${Constants.IMAGE_BASE_URL}${poster_path}"
    fun getVoteAverage() = "✮ ${vote_average?.toInt()}"
}

