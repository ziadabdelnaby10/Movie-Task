package com.example.moviestask.database

import androidx.room.*

@Dao
interface DAOAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieTableModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryTableModel)

    @Transaction
    @Query("SELECT * FROM Category WHERE categoryName =:categoryName ")
    suspend fun getMoviesByCategory(categoryName: String): CategoryWithMoviesTable

    @Transaction
    @Query("SELECT * FROM Category")
    suspend fun getMoviesWithCategory(): List<CategoryWithMoviesTable>

    @Delete
    suspend fun deleteMovie(movie: MovieTableModel)
}