package com.dvg.themovieapp.movies.data.local.datasource

import android.app.Application
import com.dvg.themovieapp.movies.data.datasource.MovieDataSource
import com.dvg.themovieapp.movies.data.local.db.MovieDatabase
import com.dvg.themovieapp.movies.data.local.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieSourceLocal(
    application: Application
) : MovieDataSource {

    private val movieDatabase = MovieDatabase.getDatabase(application)
    private val movieDao = movieDatabase.getMovieDao()

    override suspend fun getMovieData(): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            val resultLocal = loadLocalMoviesData()

            return@withContext when {
                resultLocal.isNullOrEmpty() -> Result.failure(Throwable())
                else -> Result.success(resultLocal)
            }
        }

    override suspend fun saveMovieData(movieList: List<Movie>) {
        movieDao.insertList(movieList)
    }

    override suspend fun clearMovieData() {
        movieDao.clearMoviesData()
    }

    private suspend fun loadLocalMoviesData() = movieDao.getAllMovies()
}