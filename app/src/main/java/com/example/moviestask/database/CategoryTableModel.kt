package com.example.moviestask.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryTableModel(
    @PrimaryKey(autoGenerate = false)
    var categoryName: String,
    var categoryId: Int
)