package com.example.moviestask.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class MovieRepository {
    companion object {

        private var movieDatabase: MovieDatabase? = null

        private fun initDB(context: Context): MovieDatabase {
            return MovieDatabase.getDatabaseClient(context)
        }

        suspend fun insertMovie(context: Context, movieTableModel: MovieTableModel) {
            movieDatabase = initDB(context)
            movieDatabase!!.movieDao().insertMovie(movieTableModel)

        }

        suspend fun insertCategory(context: Context, categoryTableModel: CategoryTableModel) {
            movieDatabase = initDB(context)
            movieDatabase!!.movieDao().insertCategory(categoryTableModel)

        }

        suspend fun getMoviesByCategory(context: Context, categoryName: String): CategoryWithMoviesTable {
            movieDatabase = initDB(context)
            return movieDatabase!!.movieDao().getMoviesByCategory(categoryName)
        }

        suspend fun getMoviesWithCategory(context: Context): List<CategoryWithMoviesTable> {
            movieDatabase = initDB(context)
            return movieDatabase!!.movieDao().getMoviesWithCategory()
        }

        suspend fun deleteMovie(context: Context, movie: MovieTableModel) {
            movieDatabase = initDB(context)
            movieDatabase!!.movieDao().deleteMovie(movie)
        }
    }
}