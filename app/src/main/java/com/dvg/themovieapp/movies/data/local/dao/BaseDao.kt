package com.dvg.themovieapp.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(entities: List<T>)

    @Update
    suspend fun update(entity: T)

    @Update
    suspend fun updateList(entities: List<T>)

    @Delete
    suspend fun delete(entity: T)

    @Delete
    suspend fun deleteList(entities: List<T>)
}