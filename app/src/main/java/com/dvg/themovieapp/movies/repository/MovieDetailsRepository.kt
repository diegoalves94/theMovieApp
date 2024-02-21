package com.dvg.themovieapp.movies.repository

import android.app.Application
import com.dvg.themovieapp.movies.data.local.datasource.MovieDetailsSourceLocal
import com.dvg.themovieapp.movies.data.local.entities.MovieDetail
import com.dvg.themovieapp.movies.data.local.entities.MovieMedia
import com.dvg.themovieapp.movies.data.remote.datasource.MovieDetailsSourceRemote

class MovieDetailsRepository(
    application: Application
) {

    private val movieDetailsSourceRemote = MovieDetailsSourceRemote()
    private val movieDetailsSourceLocal = MovieDetailsSourceLocal(application)

    suspend fun getMovieDetailsData(selectedMovieId: Int): Result<MovieDetail?> {
        return try {
            val result = movieDetailsSourceRemote.getMovieDetailsData(selectedMovieId)
            if (result.isSuccess) {
                persistMovieDetailsData(result.getOrNull())
                result
            } else {
                getMovieDetailsLocal(selectedMovieId)
            }
        } catch (e: Exception) {
            getMovieDetailsLocal(selectedMovieId)
        }
    }

    private suspend fun persistMovieDetailsData(movieDetail: MovieDetail?) {
        movieDetailsSourceLocal.clearMovieDetailsData(movieDetail!!.id)
        movieDetail.let {
            movieDetailsSourceLocal.saveMovieDetailsData(it)
        }
    }

    private suspend fun getMovieDetailsLocal(selectedMovieId: Int): Result<MovieDetail?> =
        movieDetailsSourceLocal.getMovieDetailsData(selectedMovieId)

    suspend fun getMovieMediaData(selectedMovieId: Int): Result<MovieMedia?> {
        return try {
            val result = movieDetailsSourceRemote.getMovieMediaData(selectedMovieId)
            if (result.isSuccess) {
                persistMovieMediaData(result.getOrNull())
                result
            } else {
                getMovieMediaLocal(selectedMovieId)
            }
        } catch (e: Exception) {
            getMovieMediaLocal(selectedMovieId)
        }
    }

    private suspend fun persistMovieMediaData(movieMedia: MovieMedia?) {
        movieDetailsSourceLocal.clearMovieMediaData(movieMedia!!.movieId!!)
        movieMedia.let {
            movieDetailsSourceLocal.saveMovieMediaData(movieMedia)
        }
    }

    private suspend fun getMovieMediaLocal(selectedMovieId: Int): Result<MovieMedia?> =
        movieDetailsSourceLocal.getMovieMediaData(selectedMovieId)

}