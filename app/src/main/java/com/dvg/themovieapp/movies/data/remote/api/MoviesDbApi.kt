package com.dvg.themovieapp.movies.data.remote.api

import com.dvg.themovieapp.movies.data.remote.services.MovieService
import com.dvg.themovieapp.movies.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20
object MoviesDbApi {

    val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter(
                "api_key",
                Constants.MOVIE_DB_API_KEY
            )
            .build()

        val request = chain.request()
            .newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZjVmMDY4YmU1ZWQxOGI2ZWE3ZTU0MjQ0YjliODQzNSIsInN1YiI6IjY0NTJlNTkyMDkxZTYyMDE4NjUwMGQzMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.u_lr7QXM9FGLIPmlprW_Ix3tcJDR0h1eN54LKT7ubdA")
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
        .baseUrl(Constants.ENDPOINT_MOVIE_DB)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    val movieService = retrofit.create(MovieService::class.java)

}