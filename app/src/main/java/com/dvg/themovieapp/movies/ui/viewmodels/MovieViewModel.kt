package com.dvg.themovieapp.movies.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvg.themovieapp.movies.data.remote.api.MoviesDbApi.movieService
import com.dvg.themovieapp.movies.data.remote.response.MovieDetailResponse
import com.dvg.themovieapp.movies.data.remote.response.MoviesResponse
import com.dvg.themovieapp.movies.models.DataState
import com.dvg.themovieapp.movies.models.Event
import com.dvg.themovieapp.movies.models.Movie
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    val movieListLiveData: LiveData<List<Movie>?>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<List<Movie>?>()

    val movieDetailsLiveData: LiveData<Movie>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<Movie>()

    val navigationToDetailsLiveData
        get() = _navigationToDetailsLiveData
    private val _navigationToDetailsLiveData = MutableLiveData<Event<Unit>>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    init {
        _appState.postValue(DataState.LOADING)
        viewModelScope.launch {
            getMoviesData()
        }
    }

    private fun getMoviesData() {
        movieService.getMoviesList("en", "1").enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    _movieListLiveData.postValue(response.body()?.results)
                    _appState.postValue(DataState.SUCCESS)
                }

            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e("API_MOVIE_DB_ERROR", t.message.toString())
                _appState.postValue(DataState.ERROR)
            }
        })
    }


    fun onMovieSelected(position: Int) {
        val movieDetails = _movieListLiveData.value?.get(position)
        movieDetails.let {
            _movieDetailsLiveData.value = it
            _navigationToDetailsLiveData.postValue(Event(Unit))
        }
    }

    private fun getMovieDetails(position: Int) {
        movieService.getMovieDetails(_movieListLiveData.value?.get(position)?.id!!, "en").enqueue(object: Callback<MovieDetailResponse>{
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                val movieDetails = Movie(
                    response.body()?.id,
                    "TEste",
                    response.body()?.overview,
                    response.body()?.release_date,
                    response.body()?.vote_average,
                    null,
                    response.body()?.poster_path
                )

                _movieDetailsLiveData.value = movieDetails
                _navigationToDetailsLiveData.postValue(Event(Unit))
                _appState.postValue(DataState.SUCCESS)
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e("API_MOVIE_DB_ERROR", t.message.toString())
                _appState.postValue(DataState.ERROR)
            }
        })
    }

}