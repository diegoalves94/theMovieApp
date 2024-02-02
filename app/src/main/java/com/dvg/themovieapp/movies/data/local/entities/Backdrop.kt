package com.dvg.themovieapp.movies.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MovieMedia::class,
            parentColumns = ["movieId"],
            childColumns = ["movieMediaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@JsonClass(generateAdapter = true)
data class Backdrop(
    @PrimaryKey(autoGenerate = true) var backdropId: Int?,
    @ColumnInfo(index = true) var movieMediaId: Int?,
    var id: Int = 0,
    var aspect_ratio: Double? = null,
    var file_path: String? = null,
    var height: Int? = null,
    var iso_639_1: String? = null,
    var vote_average: Double? = null,
    var vote_count: Int? = null,
    var width: Int? = null
)