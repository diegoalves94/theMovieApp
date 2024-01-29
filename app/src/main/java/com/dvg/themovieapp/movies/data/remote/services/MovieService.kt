package com.dvg.themovieapp.movies.data.remote.services

import com.dvg.themovieapp.movies.data.remote.response.MovieDetailResponse
import com.dvg.themovieapp.movies.data.remote.response.MovieMediaResponse
import com.dvg.themovieapp.movies.data.remote.response.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun getMoviesList(
        @Query("language") language: String,
        @Query("page") page: String,
    ): Call<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        ): Call<MovieDetailResponse>

    @GET("movie/{id}/images")
    fun getMovieMedia(
        @Path("id") movieId: Int
    ): Call<MovieMediaResponse>
}