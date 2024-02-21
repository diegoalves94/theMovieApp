package com.dvg.themovieapp.movies.data.datasource

import com.dvg.themovieapp.movies.data.local.entities.Movie

interface MovieDataSource {
    suspend fun getMovieData() : Result<List<Movie>?>
    suspend fun saveMovieData(movieList: List<Movie>) { /* default implementation */ }
    suspend fun clearMovieData() { /* default implementation */ }
}