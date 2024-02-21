package com.dvg.themovieapp.movies.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvg.themovieapp.movies.data.local.entities.Movie
import com.dvg.themovieapp.movies.data.models.DataState
import com.dvg.themovieapp.movies.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val movieRepository = MovieRepository(application)

    val movieListLiveData: LiveData<List<Movie>?>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<List<Movie>?>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    init {
        getMoviesData()
    }

    private fun getMoviesData() {
        _appState.postValue(DataState.LOADING)
        viewModelScope.launch {
            val movieList = movieRepository.getMovieData()
            movieList.fold(
                onSuccess = {
                    _movieListLiveData.value = it
                    _appState.value = DataState.SUCCESS
                },
                onFailure = {
                    _appState.value = DataState.ERROR
                }
            )
        }
    }

}