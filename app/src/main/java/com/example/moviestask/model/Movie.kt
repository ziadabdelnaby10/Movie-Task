package com.example.moviestask.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("rate") var rate: String? = null
)
