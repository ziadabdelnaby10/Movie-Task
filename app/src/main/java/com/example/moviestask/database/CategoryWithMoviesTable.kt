package com.example.moviestask.database

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithMoviesTable(
    @Embedded val categoryTableModel: CategoryTableModel,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "categoryName"
    )
    val movieTableModel: List<MovieTableModel>
)
