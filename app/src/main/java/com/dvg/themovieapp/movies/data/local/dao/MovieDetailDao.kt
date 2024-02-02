package com.dvg.themovieapp.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.dvg.themovieapp.movies.data.local.entities.MovieDetail

@Dao
interface MovieDetailDao: BaseDao<MovieDetail> {

    @Query("SELECT * FROM moviedetail WHERE id = :id")
    suspend fun getMovieDetails(id: Int): MovieDetail?

    @Query("DELETE FROM moviedetail WHERE id = :id")
    suspend fun clearMovieDetailsData(id: Int)

}