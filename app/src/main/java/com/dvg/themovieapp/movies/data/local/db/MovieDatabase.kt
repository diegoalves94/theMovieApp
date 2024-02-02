package com.dvg.themovieapp.movies.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dvg.themovieapp.movies.data.local.dao.BackdropDao
import com.dvg.themovieapp.movies.data.local.dao.MovieDao
import com.dvg.themovieapp.movies.data.local.dao.MovieDetailDao
import com.dvg.themovieapp.movies.data.local.dao.MovieMediaDao
import com.dvg.themovieapp.movies.data.local.entities.Backdrop
import com.dvg.themovieapp.movies.data.local.entities.Movie
import com.dvg.themovieapp.movies.data.local.entities.MovieDetail
import com.dvg.themovieapp.movies.data.local.entities.MovieMedia

@Database(
    entities = [Movie::class, MovieDetail::class, MovieMedia::class, Backdrop::class],
    version = 5,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
    abstract fun getMovieDetailDao(): MovieDetailDao

    abstract fun backdropsDao(): BackdropDao
    abstract fun getMovieMediaDao(movieDatabase: MovieDatabase): MovieMediaDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).fallbackToDestructiveMigration()
                    .build()
                this.instance = database
                return database
            }
        }
    }
}