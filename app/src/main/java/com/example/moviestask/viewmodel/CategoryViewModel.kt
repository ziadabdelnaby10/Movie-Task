package com.example.moviestask.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviestask.database.CategoryTableModel
import com.example.moviestask.database.CategoryWithMoviesTable
import com.example.moviestask.database.MovieRepository
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val _liveDataCategories = MutableLiveData<List<CategoryWithMoviesTable>>()
    val liveDataCategories: LiveData<List<CategoryWithMoviesTable>>
        get() = _liveDataCategories
    private var context: Context

    init {
        context = application.applicationContext
        //fetch
    }

    fun fetchData() {
        viewModelScope.launch {
            val categories = MovieRepository.getMoviesWithCategory(context)
            _liveDataCategories.postValue(categories)
        }
    }

    fun addCategory(category: CategoryTableModel) {
        viewModelScope.launch {
            MovieRepository.insertCategory(context, category)
            fetchData()
        }

    }


}