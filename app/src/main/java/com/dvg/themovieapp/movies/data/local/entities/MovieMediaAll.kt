package com.dvg.themovieapp.movies.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MovieMediaAll(
    @Embedded var movieMedia: MovieMedia,

    @Relation(
        entity = Backdrop::class,
        parentColumn = "movieId",
        entityColumn = "movieMediaId"
    ) var backdrop: List<Backdrop>
)
