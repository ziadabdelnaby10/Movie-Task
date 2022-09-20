package com.example.moviestask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CategoryTableModel::class, MovieTableModel::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): DAOAccess

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null
        fun getDatabaseClient(context: Context): MovieDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE =
                    Room.databaseBuilder(context, MovieDatabase::class.java, "APP_DATABASE")
                        .fallbackToDestructiveMigration()
                        .build()
                return INSTANCE!!
            }
        }
    }
}