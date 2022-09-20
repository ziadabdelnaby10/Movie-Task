package com.example.moviestask.model

import com.google.gson.annotations.SerializedName

data class MoviesData(
    @SerializedName("categories") var categories: ArrayList<Category> = arrayListOf()
)
