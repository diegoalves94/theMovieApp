package com.dvg.themovieapp.movies.data.remote.datasource

import com.dvg.themovieapp.movies.data.datasource.MovieDataSource
import com.dvg.themovieapp.movies.data.local.entities.Movie
import com.dvg.themovieapp.movies.data.remote.api.MoviesDbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieSourceRemote : MovieDataSource {

    override suspend fun getMovieData(): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            val response = MoviesDbApi.movieService.getMoviesList("en", "1")

            return@withContext when {
                response.isSuccessful -> Result.success(response.body()?.results)
                else -> Result.failure(Throwable(response.message()))
            }
        }

}