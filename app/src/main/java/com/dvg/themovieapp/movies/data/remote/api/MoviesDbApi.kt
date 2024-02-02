package com.dvg.themovieapp.movies.data.remote.api

import com.dvg.themovieapp.movies.data.remote.services.MovieService
import com.dvg.themovieapp.movies.utils.Constants.ENDPOINT_MOVIE_DB
import com.dvg.themovieapp.movies.utils.Constants.MOVIE_DB_API_KEY
import com.dvg.themovieapp.movies.utils.Constants.MOVIE_DB_AUTH_BEARER
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
object MoviesDbApi {

    private val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter(
                "api_key",
                MOVIE_DB_API_KEY
            )
            .build()

        val request = chain.request()
            .newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", MOVIE_DB_AUTH_BEARER)
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(ENDPOINT_MOVIE_DB)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val movieService: MovieService = retrofit.create(MovieService::class.java)

}