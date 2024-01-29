package com.dvg.themovieapp.movies.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvg.themovieapp.movies.data.remote.api.MoviesDbApi.movieService
import com.dvg.themovieapp.movies.data.remote.response.MovieMediaResponse
import com.dvg.themovieapp.movies.models.DataState
import com.dvg.themovieapp.movies.models.Movie
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {

    private val selectedMovieId: LiveData<Int>
        get() = _selectedMovieId
    private val _selectedMovieId = MutableLiveData<Int>()

    val movieDetailsLiveData: LiveData<Movie?>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<Movie?>()

    val movieImagesLiveData: LiveData<MovieMediaResponse?>
        get() = _movieImagesLiveData
    private val _movieImagesLiveData = MutableLiveData<MovieMediaResponse?>()

    val movieState: LiveData<DataState>
        get() = _movieState
    private val _movieState = MutableLiveData<DataState>()

    val imagesCarouselState: LiveData<DataState>
        get() = _imagesCarouselState
    private val _imagesCarouselState = MutableLiveData<DataState>()

    fun setMovieData(id: Int) {
        _selectedMovieId.value = id
        _movieState.postValue(DataState.LOADING)

        viewModelScope.launch {
            val response = movieService.getMovieDetails(selectedMovieId.value!!, "en")

            if (response.isSuccessful) {
                response.body()?.let { movie ->

                    val movieDetails = Movie(
                        movie.id,
                        movie.title,
                        movie.overview,
                        movie.release_date,
                        movie.vote_average,
                        null,
                        movie.poster_path
                    )

                    _movieDetailsLiveData.value = movieDetails
                    _movieState.postValue(DataState.SUCCESS)
                }
            } else {
                _movieState.postValue(DataState.ERROR)
            }
        }
    }

    fun setMovieImageCarousel() {
        _imagesCarouselState.postValue(DataState.LOADING)

        viewModelScope.launch {
            val response = movieService.getMovieMedia(selectedMovieId.value!!)

            if (response.isSuccessful) {
                response.body()?.backdrops?.let {
                    if (it.isEmpty()) {
                        _imagesCarouselState.postValue(DataState.ERROR)
                    } else {
                        _movieImagesLiveData.value = response.body()
                        _imagesCarouselState.postValue(DataState.SUCCESS)
                    }
                }
            } else {
                _imagesCarouselState.postValue(DataState.ERROR)
            }
        }
    }
}