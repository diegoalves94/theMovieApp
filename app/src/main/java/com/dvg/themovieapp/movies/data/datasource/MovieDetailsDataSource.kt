package com.dvg.themovieapp.movies.data.datasource

import com.dvg.themovieapp.movies.data.local.entities.MovieDetail
import com.dvg.themovieapp.movies.data.local.entities.MovieMedia

interface MovieDetailsDataSource {

    suspend fun getMovieDetailsData(selectedMovieId: Int) : Result<MovieDetail?>
    suspend fun saveMovieDetailsData(movieDetails: MovieDetail) { /* default implementation */ }
    suspend fun clearMovieDetailsData(selectedMovieId: Int) { /* default implementation */ }

    suspend fun getMovieMediaData(selectedMovieId: Int) : Result<MovieMedia?>
    suspend fun saveMovieMediaData(movieMedia: MovieMedia) { /* default implementation */ }
    suspend fun clearMovieMediaData(selectedMovieId: Int) { /* default implementation */ }

}