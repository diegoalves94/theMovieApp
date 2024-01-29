package com.dvg.themovieapp.movies.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvg.themovieapp.movies.data.remote.api.MoviesDbApi
import com.dvg.themovieapp.movies.data.remote.response.MovieDetailResponse
import com.dvg.themovieapp.movies.data.remote.response.MovieMediaResponse
import com.dvg.themovieapp.movies.models.Backdrop
import com.dvg.themovieapp.movies.models.DataState
import com.dvg.themovieapp.movies.models.Movie
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsViewModel : ViewModel() {

    val selectedMovieId: LiveData<Int>
        get() = _selectedMovieId
    private val _selectedMovieId = MutableLiveData<Int>()

    val movieDetailsLiveData: LiveData<Movie?>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<Movie?>()

    val movieImagesLiveData: LiveData<List<Backdrop>?>
        get() = _movieImagesLiveData
    private val _movieImagesLiveData = MutableLiveData<List<Backdrop>?>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    init {
        _appState.postValue(DataState.LOADING)
    }

    fun setMovieData(id:Int){
        _selectedMovieId.value = id
        viewModelScope.launch{
            getMovieDetails()
            getMovieImageCaroussel()
        }
    }

    private fun getMovieDetails() {
        MoviesDbApi.movieService.getMovieDetails(selectedMovieId.value!!, "en").enqueue(object: Callback<MovieDetailResponse>{
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                val movieDetails = Movie(
                    response.body()?.id,
                    response.body()?.title,
                    response.body()?.overview,
                    response.body()?.release_date,
                    response.body()?.vote_average,
                    null,
                    response.body()?.poster_path
                )

                _movieDetailsLiveData.value = movieDetails
                _appState.postValue(DataState.SUCCESS)
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e("API_MOVIE_DB_ERROR", t.message.toString())
                _appState.postValue(DataState.ERROR)
            }
        })
    }

    private fun getMovieImageCaroussel(){
        MoviesDbApi.movieService.getMovieMedia(selectedMovieId.value!!).enqueue(object : Callback<MovieMediaResponse>{
            override fun onResponse(
                call: Call<MovieMediaResponse>,
                response: Response<MovieMediaResponse>
            ) {
                if(response.isSuccessful){
                    _movieImagesLiveData.value = response.body()?.backdrops
                }
            }

            override fun onFailure(call: Call<MovieMediaResponse>, t: Throwable) {
                Log.e("API_MOVIE_DB_ERROR", t.message.toString())
                _appState.postValue(DataState.ERROR)
            }
        })
    }
}