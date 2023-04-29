package com.dvg.themovieapp.movies.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvg.themovieapp.databinding.FragmentMovieItemBinding
import com.dvg.themovieapp.movies.models.Movie

class MovieListAdapter(
    private val listener: OnMovieItemClickListener
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private val moviesList: MutableList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]

        holder.bindItem(movie)
        holder.view.setOnClickListener {
            listener.onMovieSelected(position)
        }
    }

    override fun getItemCount(): Int = moviesList.size

    inner class ViewHolder(val binding: FragmentMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root
        fun bindItem(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    fun updateData(movieList: List<Movie>) {
        moviesList.clear()
        moviesList.addAll(movieList)
        notifyDataSetChanged()
    }

}

interface OnMovieItemClickListener {
    fun onMovieSelected(position: Int)
}