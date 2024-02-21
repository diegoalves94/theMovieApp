package com.dvg.themovieapp.movies.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvg.themovieapp.movies.data.local.entities.MovieDetail
import com.dvg.themovieapp.movies.data.local.entities.MovieMedia
import com.dvg.themovieapp.movies.data.models.DataState
import com.dvg.themovieapp.movies.repository.MovieDetailsRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val movieDetailRepository = MovieDetailsRepository(application)

    private val selectedMovieId: LiveData<Int>
        get() = _selectedMovieId
    private val _selectedMovieId = MutableLiveData<Int>()

    val movieDetailsLiveData: LiveData<MovieDetail?>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<MovieDetail?>()

    val movieImagesLiveData: LiveData<MovieMedia?>
        get() = _movieImagesLiveData
    private val _movieImagesLiveData = MutableLiveData<MovieMedia?>()

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
            val movieDetails = movieDetailRepository.getMovieDetailsData(id)
            movieDetails.fold(
                onSuccess = {
                    _movieDetailsLiveData.postValue(it)
                    _movieState.postValue(DataState.SUCCESS)
                },
                onFailure = {
                    _movieState.postValue(DataState.ERROR)
                }
            )
        }
    }


    fun setMovieImageCarousel() {
        _imagesCarouselState.postValue(DataState.LOADING)

        viewModelScope.launch {
            val movieMedia = movieDetailRepository.getMovieMediaData(selectedMovieId.value!!)
            movieMedia.fold(
                onSuccess = {
                    _movieImagesLiveData.value = it
                    _imagesCarouselState.postValue(DataState.SUCCESS)
                },
                onFailure = {
                    _imagesCarouselState.postValue(DataState.ERROR)
                }
            )
        }
    }

}