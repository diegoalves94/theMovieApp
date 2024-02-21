package com.dvg.themovieapp.movies.repository

import android.app.Application
import com.dvg.themovieapp.movies.data.local.datasource.MovieSourceLocal
import com.dvg.themovieapp.movies.data.local.entities.Movie
import com.dvg.themovieapp.movies.data.remote.datasource.MovieSourceRemote

class MovieRepository(
    application: Application
) {

    private val movieSourceRemote = MovieSourceRemote()
    private val movieSourceLocal = MovieSourceLocal(application)

    suspend fun getMovieData(): Result<List<Movie>?> {
        return try {
            val result = movieSourceRemote.getMovieData()
            if (result.isSuccess) {
                persistMovieData(result.getOrNull())
                result
            } else {
                getMovieLocal()
            }
        } catch (e: Exception) {
            getMovieLocal()
        }
    }

    private suspend fun persistMovieData(movieList: List<Movie>?) {
        movieSourceLocal.clearMovieData()
        movieList?.let {
            movieSourceLocal.saveMovieData(it)
        }
    }

    private suspend fun getMovieLocal(): Result<List<Movie>?> = movieSourceLocal.getMovieData()

}