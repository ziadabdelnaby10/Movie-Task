package com.example.moviestask.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class MovieTableModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var description: String,
    var rate: String,
    var categoryName: String
)
