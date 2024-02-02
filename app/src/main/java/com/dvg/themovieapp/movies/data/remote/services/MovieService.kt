package com.dvg.themovieapp.movies.data.remote.services

import com.dvg.themovieapp.movies.data.remote.response.MovieDetailResponse
import com.dvg.themovieapp.movies.data.remote.response.MovieMediaResponse
import com.dvg.themovieapp.movies.data.remote.response.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface MovieService {
    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("language") language: String,
        @Query("page") page: String,
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        ): Response<MovieDetailResponse>

    @GET("movie/{id}/images")
    suspend fun getMovieMedia(
        @Path("id") movieId: Int
    ): Response<MovieMediaResponse>
}