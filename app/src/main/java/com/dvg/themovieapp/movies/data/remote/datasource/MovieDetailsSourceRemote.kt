package com.dvg.themovieapp.movies.data.remote.datasource

import com.dvg.themovieapp.movies.data.datasource.MovieDetailsDataSource
import com.dvg.themovieapp.movies.data.local.entities.MovieDetail
import com.dvg.themovieapp.movies.data.local.entities.MovieMedia
import com.dvg.themovieapp.movies.data.remote.api.MoviesDbApi
import com.dvg.themovieapp.movies.data.remote.response.MovieDetailResponse
import com.dvg.themovieapp.movies.data.remote.response.MovieMediaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsSourceRemote : MovieDetailsDataSource {

    override suspend fun getMovieDetailsData(selectedMovieId: Int): Result<MovieDetail?> =
        withContext(Dispatchers.IO) {
            val response = MoviesDbApi.movieService.getMovieDetails(selectedMovieId, "en")

            return@withContext when {
                response.isSuccessful -> Result.success(mapMovieDetails(response.body()))
                else -> Result.failure(Throwable(response.message()))
            }
        }

    private fun mapMovieDetails(response: MovieDetailResponse?): MovieDetail? {
        response?.let {
            return MovieDetail(
                response.id,
                response.title,
                response.overview,
                response.release_date,
                response.vote_average,
                response.poster_path
            )
        }
        return null
    }

    override suspend fun getMovieMediaData(selectedMovieId: Int): Result<MovieMedia?> =
        withContext(Dispatchers.IO) {
            val response = MoviesDbApi.movieService.getMovieMedia(selectedMovieId)

            return@withContext when {
                response.isSuccessful -> {
                    response.body().let {
                        if (it?.backdrops.isNullOrEmpty()) {
                            return@let Result.failure(Throwable(response.message()))
                        } else {
                            return@let Result.success(mapMovieMedia(response.body()))
                        }
                    }
                }

                else -> Result.failure(Throwable(response.message()))
            }
        }

    private fun mapMovieMedia(response: MovieMediaResponse?): MovieMedia? {
        response?.let {
            return MovieMedia(
                it.id,
                it.backdrops
            )
        }
        return null
    }

}