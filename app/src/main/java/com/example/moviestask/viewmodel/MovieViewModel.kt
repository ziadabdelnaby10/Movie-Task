package com.example.moviestask.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviestask.database.MovieRepository
import com.example.moviestask.database.MovieTableModel
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val _liveDataMovies = MutableLiveData<List<MovieTableModel>>()
    val liveDataMovies: LiveData<List<MovieTableModel>>
        get() = _liveDataMovies

    private lateinit var context: Context

    init {
        context = application.applicationContext
        //fetch
    }

    fun fetchData(category: String) {
        viewModelScope.launch {
            val movies = MovieRepository.getMoviesByCategory(context, category).movieTableModel
            _liveDataMovies.postValue(movies)
        }
    }

    fun insertMovie(movie: MovieTableModel) {
        viewModelScope.launch {
            MovieRepository.insertMovie(context, movie)
            fetchData(movie.categoryName)
        }
    }

    fun editMovie(movie: MovieTableModel) {
        viewModelScope.launch {
            MovieRepository.insertMovie(context, movie)
            fetchData(movie.categoryName)
        }
    }

    fun deleteMovie(movie: MovieTableModel) {
        viewModelScope.launch {
            MovieRepository.deleteMovie(context, movie)
            fetchData(movie.categoryName)
        }
    }
}