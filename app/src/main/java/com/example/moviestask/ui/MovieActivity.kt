package com.example.moviestask.ui

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.moviestask.adapter.MovieRecyclerAdapter
import com.example.moviestask.database.MovieTableModel
import com.example.moviestask.databinding.ActivityMovieBinding
import com.example.moviestask.model.Category
import com.example.moviestask.model.MoviesData
import com.example.moviestask.viewmodel.MovieViewModel
import com.google.gson.Gson
import java.io.IOException


class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private val model: MovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.extras?.getString("Name")

        val sharedPref = getSharedPreferences("Movie", Context.MODE_PRIVATE)

        val response = sharedPref.getString("Movie", null)
        if (response == null) {
            val editor = sharedPref.edit()
            val categories = getJsonDataFromAsset(applicationContext, "movies-data.json")
            categories.forEach { category ->
                category.movies.forEach { movie ->
                    model.insertMovie(
                        MovieTableModel(
                            movie.id,
                            movie.name!!,
                            movie.description!!,
                            movie.rate!!,
                            category.name!!
                        )
                    )
                }
            }
            editor.apply {
                putString("Movie", "Done")
                apply()
            }
            model.fetchData(categoryName!!)
        } else {
            model.fetchData(categoryName!!)
        }


        model.liveDataMovies.observe(this) {
            binding.rvMovie.adapter = MovieRecyclerAdapter(
                it,
                { movieEdit ->
                    createEditDialog(applicationContext, movieEdit)
                },
                { movieDelete ->
                    createDeleteDialog(applicationContext, movieDelete)
                })
        }

        binding.fabAddMovie.setOnClickListener {
            createInsertDialog(this, categoryName!!)
        }
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): ArrayList<Category> {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("Main Activity ,Json", ioException.message.toString())
            return ArrayList()
        }
        var data = Gson().fromJson(jsonString, MoviesData::class.java)
        return data.categories
    }

    private fun createInsertDialog(context: Context, categoryName: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter The Movie Data")
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val name = EditText(this)
        val desc = EditText(this)
        val rate = EditText(this)
        name.inputType = InputType.TYPE_CLASS_TEXT
        desc.inputType = InputType.TYPE_CLASS_TEXT
        rate.inputType = InputType.TYPE_CLASS_NUMBER
        name.hint = "Movie Name"
        desc.hint = "Description"
        rate.hint = "Rate"
        layout.addView(name)
        layout.addView(desc)
        layout.addView(rate)
        builder.setView(layout)
        builder.setPositiveButton("Save") { i, j ->
            model.insertMovie(
                MovieTableModel(
                    null,
                    name.text.toString(),
                    desc.text.toString(),
                    rate.text.toString(),
                    categoryName
                )
            )
            binding.rvMovie.adapter?.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.create()
        builder.show()
    }

    private fun createEditDialog(context: Context, movieTableModel: MovieTableModel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit The Movie")
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val name = EditText(this)
        val desc = EditText(this)
        val rate = EditText(this)
        name.inputType = InputType.TYPE_CLASS_TEXT
        desc.inputType = InputType.TYPE_CLASS_TEXT
        rate.inputType = InputType.TYPE_CLASS_NUMBER
        name.hint = "Movie Name"
        desc.hint = "Description"
        rate.hint = "Rate"
        name.setText(movieTableModel.name)
        desc.setText(movieTableModel.description)
        rate.setText(movieTableModel.rate)
        layout.addView(name)
        layout.addView(desc)
        layout.addView(rate)
        builder.setView(layout)
        builder.setPositiveButton("Save") { i, j ->
            model.insertMovie(
                MovieTableModel(
                    movieTableModel.id,
                    name.text.toString(),
                    desc.text.toString(),
                    rate.text.toString(),
                    movieTableModel.categoryName
                )
            )
            binding.rvMovie.adapter?.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.create()
        builder.show()
    }

    private fun createDeleteDialog(context: Context, movieTableModel: MovieTableModel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure you want to delete ${movieTableModel.name}?")
        builder.setPositiveButton("Save") { i, j ->
            model.deleteMovie(movieTableModel)
            binding.rvMovie.adapter?.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.create()
        builder.show()
    }
}