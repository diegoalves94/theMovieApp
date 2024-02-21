package com.dvg.themovieapp.movies.data.local.datasource

import android.app.Application
import com.dvg.themovieapp.movies.data.datasource.MovieDetailsDataSource
import com.dvg.themovieapp.movies.data.local.db.MovieDatabase
import com.dvg.themovieapp.movies.data.local.entities.MovieDetail
import com.dvg.themovieapp.movies.data.local.entities.MovieMedia
import com.dvg.themovieapp.movies.data.local.entities.MovieMediaAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsSourceLocal(
    application: Application
) : MovieDetailsDataSource {

    private val movieDatabase = MovieDatabase.getDatabase(application)
    private val movieDetailsDao = movieDatabase.getMovieDetailDao()
    private val movieMediaDao = movieDatabase.getMovieMediaDao(movieDatabase)

    override suspend fun getMovieDetailsData(selectedMovieId: Int): Result<MovieDetail?> =
        withContext(Dispatchers.IO) {
            return@withContext when (val resultLocal = loadLocalMovieDetailsData(selectedMovieId)) {
                null -> Result.failure(Throwable())
                else -> Result.success(resultLocal)
            }
        }

    override suspend fun clearMovieDetailsData(selectedMovieId: Int) {
        movieDetailsDao.clearMovieDetailsData(selectedMovieId)
    }

    override suspend fun saveMovieDetailsData(movieDetails: MovieDetail) {
        movieDetailsDao.insert(movieDetails)
    }

    private suspend fun loadLocalMovieDetailsData(selectedMovieId: Int) =
        movieDetailsDao.getMovieDetails(selectedMovieId)


    override suspend fun getMovieMediaData(selectedMovieId: Int): Result<MovieMedia?> =
        withContext(Dispatchers.IO) {
            return@withContext when (val resultMediaLocal =
                loadLocalMovieMediaData(selectedMovieId)) {
                null -> Result.failure(Throwable())
                else -> Result.success(resultMediaLocal)
            }
        }

    override suspend fun clearMovieMediaData(selectedMovieId: Int) {
        movieMediaDao.clearMoviesMediaData(selectedMovieId)
    }

    override suspend fun saveMovieMediaData(movieMedia: MovieMedia) {
        movieMediaDao.insertMovieMedia(movieMedia)
    }

    private suspend fun loadLocalMovieMediaData(selectedMovieId: Int) =
        mapMovieMediaAllToMovieMedia(movieMediaDao.getMovieMedia(selectedMovieId))

    private fun mapMovieMediaAllToMovieMedia(movieMediaAll: MovieMediaAll?): MovieMedia? {
        return if (movieMediaAll != null) {
            movieMediaAll.movieMedia.movieId = movieMediaAll.movieMedia.movieId
            movieMediaAll.movieMedia.backdrops = movieMediaAll.backdrop

            movieMediaAll.movieMedia
        } else {
            return null
        }
    }

}