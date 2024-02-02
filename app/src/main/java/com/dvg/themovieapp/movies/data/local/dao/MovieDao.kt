package com.dvg.themovieapp.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.dvg.themovieapp.movies.data.local.entities.Movie

@Dao
interface MovieDao : BaseDao<Movie> {
    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<Movie>?

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovie(id: Int): Movie?

    @Query("DELETE FROM movie")
    suspend fun clearMoviesData()
}