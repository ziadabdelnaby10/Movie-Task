package com.example.moviestask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviestask.database.CategoryWithMoviesTable
import com.example.moviestask.databinding.CategoryRecyclerItemBinding

class CategoryRecyclerAdapter(
    var data: MutableList<CategoryWithMoviesTable>,
    val onClick: (CategoryWithMoviesTable) -> Unit
) :
    RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            CategoryRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CategoryViewHolder(private val binding: CategoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onClick(data[adapterPosition])
            }
        }

        fun bind(categories: CategoryWithMoviesTable) = binding.apply {
            txtCategory.text = categories.categoryTableModel.categoryName
        }
    }
}
