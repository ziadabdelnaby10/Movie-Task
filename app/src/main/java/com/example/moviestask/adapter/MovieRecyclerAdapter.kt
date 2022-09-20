package com.example.moviestask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviestask.database.MovieTableModel
import com.example.moviestask.databinding.MovieRecyclerItemBinding

class MovieRecyclerAdapter(
    var data: List<MovieTableModel>,
    val onClick: (MovieTableModel) -> Unit,
    val onLongClick: (MovieTableModel) -> Unit
) :
    RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding =
            MovieRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MovieViewHolder(private val binding: MovieRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onClick(data[adapterPosition])
            }

            binding.root.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onLongClick(data[adapterPosition])
                true
            }
        }

        fun bind(movie: MovieTableModel) = binding.apply {
            txtName.text = movie.name
            txtDescription.text = movie.description
        }
    }


}