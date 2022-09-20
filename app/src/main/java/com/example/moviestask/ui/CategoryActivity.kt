package com.example.moviestask.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import com.example.moviestask.adapter.CategoryRecyclerAdapter
import com.example.moviestask.database.CategoryTableModel
import com.example.moviestask.databinding.ActivityCategoryBinding
import com.example.moviestask.model.Category
import com.example.moviestask.model.MoviesData
import com.example.moviestask.viewmodel.CategoryViewModel
import com.google.gson.Gson
import java.io.IOException


class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    private val model: CategoryViewModel by viewModels()

    //val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("Category" , Context.MODE_PRIVATE)

        val response = sharedPref.getString("Category" , null)
        if (response == null) {
            val editor = sharedPref.edit()
            val categories = getJsonDataFromAsset(applicationContext, "movies-data.json")
            categories.forEach { i ->
                model.addCategory(CategoryTableModel(i.name!!, i.id!!))
            }
            editor.apply {
                putString("Category", "Done")
                apply()
            }
            model.fetchData()
        } else {
            model.fetchData()
        }


        model.liveDataCategories.observe(this) { it ->
            binding.rvCategory.adapter = CategoryRecyclerAdapter(it.toMutableList()) {
                startActivity(
                    Intent(this.applicationContext, MovieActivity::class.java).putExtra(
                        "Name",
                        it.categoryTableModel.categoryName
                    )
                )
            }
        }

        binding.fabAddCategory.setOnClickListener {
            createDialog(this)
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

    private fun createDialog(context: Context) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter The Category Name")
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val id = EditText(this)
        val name = EditText(this)
        name.inputType = InputType.TYPE_CLASS_TEXT
        id.inputType = InputType.TYPE_CLASS_NUMBER
        name.hint = "Category Name"
        id.hint = "ID"
        layout.addView(id)
        layout.addView(name)
        builder.setView(layout)
        builder.setPositiveButton("Save") { i, j ->
            model.addCategory(
                CategoryTableModel(
                    name.text.toString(),
                    Integer.parseInt(id.text.toString())
                )
            )
            binding.rvCategory.adapter?.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.create()
        builder.show()
    }
}