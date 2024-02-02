package com.dvg.themovieapp.movies.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvg.themovieapp.movies.data.local.db.MovieDatabase
import com.dvg.themovieapp.movies.data.remote.api.MoviesDbApi.movieService
import com.dvg.themovieapp.movies.data.models.DataState
import com.dvg.themovieapp.movies.data.local.entities.Movie
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val movieDatabase = MovieDatabase.getDatabase(application)
    private val movieDao = movieDatabase.getMovieDao()

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
            try {
                val response = movieService.getMoviesList("en", "1")

                if (response.isSuccessful) {
                    val moviesList = response.body()?.results

                    moviesList?.let {
                        saveMoviesData(it)
                    }

                    _movieListLiveData.postValue(moviesList)
                    _appState.postValue(DataState.SUCCESS)
                } else {
                    handleError()
                }
            } catch (e: Exception) {
                handleError()
                Log.e("CALL_MOVIE_LIST", e.message.toString())
            }
        }
    }

    private suspend fun saveMoviesData(movieList: List<Movie>) {
        movieDao.clearMoviesData()
        movieDao.insertList(movieList)
    }

    private suspend fun loadLocalMoviesData() = movieDao.getAllMovies()

    private suspend fun handleError() {
        val moviesList = loadLocalMoviesData()
        if (moviesList.isNullOrEmpty()) {
            _appState.postValue(DataState.ERROR)
        } else {
            _movieListLiveData.postValue(moviesList)
            _appState.postValue(DataState.SUCCESS)
        }
    }

}