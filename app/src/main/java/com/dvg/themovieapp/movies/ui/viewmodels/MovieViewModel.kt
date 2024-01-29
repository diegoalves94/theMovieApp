package com.dvg.themovieapp.movies.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvg.themovieapp.movies.data.remote.api.MoviesDbApi.movieService
import com.dvg.themovieapp.movies.models.DataState
import com.dvg.themovieapp.movies.models.Event
import com.dvg.themovieapp.movies.models.Movie
import kotlinx.coroutines.launch

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
        getMoviesData()
    }

    private fun getMoviesData() {
        _appState.postValue(DataState.LOADING)
        viewModelScope.launch {
            val response = movieService.getMoviesList("en", "1")

            if (response.isSuccessful) {
                _movieListLiveData.postValue(response.body()?.results)
                _appState.postValue(DataState.SUCCESS)
            } else {
                _appState.postValue(DataState.ERROR)
            }
        }
    }


    fun onMovieSelected(position: Int) {
        val movieDetails = _movieListLiveData.value?.get(position)
        movieDetails.let {
            _movieDetailsLiveData.value = it
            _navigationToDetailsLiveData.postValue(Event(Unit))
        }
    }
}