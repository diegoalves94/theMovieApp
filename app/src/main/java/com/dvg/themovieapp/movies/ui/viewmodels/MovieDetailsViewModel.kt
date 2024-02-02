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
import com.dvg.themovieapp.movies.data.local.entities.MovieDetail
import com.dvg.themovieapp.movies.data.local.entities.MovieMedia
import com.dvg.themovieapp.movies.data.local.entities.MovieMediaAll
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDetailsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val movieDatabase = MovieDatabase.getDatabase(application)
    private val movieDetailsDao = movieDatabase.getMovieDetailDao()
    private val movieMediaDao = movieDatabase.getMovieMediaDao(movieDatabase)


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
            try {
                val response = movieService.getMovieDetails(selectedMovieId.value!!, "en")

                if (response.isSuccessful) {
                    val movieResponse = response.body()

                    movieResponse?.let {
                        val movieDetails = MovieDetail(
                            it.id,
                            it.title,
                            it.overview,
                            it.release_date,
                            it.vote_average,
                            it.poster_path
                        )

                        saveMovieDetailData(movieDetails)

                        _movieDetailsLiveData.postValue(movieDetails)
                        _movieState.postValue(DataState.SUCCESS)
                    }

                } else {
                    handleMovieDetailsError()
                }
            } catch (e: Exception) {
                handleMovieDetailsError()
                Log.e("CALL_MOVIE_DETAILS", e.message.toString())
            }
        }
    }

    private suspend fun saveMovieDetailData(movieDetails: MovieDetail) {
        movieDetailsDao.clearMovieDetailsData(movieDetails.id)
        movieDetailsDao.insert(movieDetails)
    }

    private suspend fun loadLocalMovieDetailsData() =
        movieDetailsDao.getMovieDetails(selectedMovieId.value!!)

    private suspend fun handleMovieDetailsError() {
        val movieDetails = loadLocalMovieDetailsData()

        if (movieDetails != null) {
            _movieDetailsLiveData.postValue(movieDetails)
            _movieState.postValue(DataState.SUCCESS)
        } else {
            _movieState.postValue(DataState.ERROR)
        }
    }


    fun setMovieImageCarousel() {
        _imagesCarouselState.postValue(DataState.LOADING)

        viewModelScope.launch {
            try {
                val response = movieService.getMovieMedia(selectedMovieId.value!!)

                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.backdrops.isNullOrEmpty()) {
                            handleMovieMediaError()
                            _imagesCarouselState.postValue(DataState.ERROR)
                        } else {
                            val movieMedia = MovieMedia(
                                it.id,
                                it.backdrops
                            )

                            saveMovieMediaData(movieMedia)

                            _movieImagesLiveData.value = movieMedia
                            _imagesCarouselState.postValue(DataState.SUCCESS)
                        }
                    }
                } else {
                    handleMovieMediaError()
                    _imagesCarouselState.postValue(DataState.ERROR)
                }
            } catch (e: Exception) {
                handleMovieMediaError()
                Log.e("CALL_MOVIE_MEDIA", e.message.toString())
            }
        }
    }

    private suspend fun saveMovieMediaData(movieMedia: MovieMedia) {
        movieMediaDao.clearMoviesMediaData(selectedMovieId.value!!)
        movieMediaDao.insertMovieMedia(movieMedia)
    }

    private suspend fun loadLocalMovieMediaData() =
        mapMovieMediaAllToMovieMedia(movieMediaDao.getMovieMedia(selectedMovieId.value!!))

    private fun mapMovieMediaAllToMovieMedia(movieMediaAll: MovieMediaAll): MovieMedia {
        return if (movieMediaAll != null) {
            movieMediaAll.movieMedia.movieId = movieMediaAll.movieMedia.movieId
            movieMediaAll.movieMedia.backdrops = movieMediaAll.backdrop

            movieMediaAll.movieMedia
        } else {
            return MovieMedia(null, null)
        }
    }

    private suspend fun handleMovieMediaError() {
        val movieMedia = loadLocalMovieMediaData()
        if (movieMedia.backdrops.isNullOrEmpty()) {
            _imagesCarouselState.postValue(DataState.ERROR)
        } else {
            _movieImagesLiveData.postValue(movieMedia)
            _imagesCarouselState.postValue(DataState.SUCCESS)
        }
    }

}