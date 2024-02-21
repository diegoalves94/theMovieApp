package com.dvg.themovieapp.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dvg.themovieapp.movies.data.local.db.MovieDatabase
import com.dvg.themovieapp.movies.data.local.entities.MovieMedia

@Dao
abstract class MovieMediaDao(
    movieDatabase: MovieDatabase
) : BaseDao<MovieMedia> {

    private val backdropDao = movieDatabase.backdropsDao()

    @Transaction
    open suspend fun insertMovieMedia(movieMedia: MovieMedia) {
        movieMedia.backdrops?.forEach {
            it.movieMediaId = movieMedia.movieId
        }

        insert(movieMedia)
        movieMedia.backdrops?.let {
            backdropDao.insertList(it)
        }
    }

    @Transaction
    @Query("SELECT * FROM moviemedia WHERE movieId = :id")
    abstract suspend fun getMovieMedia(id: Int): com.dvg.themovieapp.movies.data.local.entities.MovieMediaAll?

    @Query("DELETE FROM moviemedia WHERE movieId = :id")
    abstract suspend fun clearMoviesMediaData(id: Int)
}