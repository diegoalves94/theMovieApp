package com.dvg.themovieapp.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvg.themovieapp.movies.models.DataState
import com.dvg.themovieapp.movies.models.Event
import com.dvg.themovieapp.movies.models.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MovieViewModel : ViewModel() {
    val movieListLiveData: LiveData<MutableList<Movie>>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<MutableList<Movie>>()

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
        viewModelScope.launch{
            delay(3000)
            getMoviesData()
        }
    }

    private fun getMoviesData() {
        _movieListLiveData.postValue(setMoviesList())
        _appState.postValue(DataState.SUCCESS)
    }

    private fun setMoviesList(): MutableList<Movie> {
        val movies = mutableListOf<Movie>()
        for (i in 0..10) {
            movies.add(
                Movie(
                    "Super Movie Hero $i: Too hero!",
                    "Rating: ${Random.nextInt(50, 100)}%",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker inclu"
                )
            )
        }
        return movies
    }

    fun onMovieSelected(position: Int) {
        val movieDetails = _movieListLiveData.value?.get(position)
        movieDetails.let {
            _movieDetailsLiveData.value = it
            _navigationToDetailsLiveData.postValue(Event(Unit))
        }
    }

}