package com.dvg.themovieapp.movies.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvg.themovieapp.databinding.FragmentMovieDetailsBinding
import com.dvg.themovieapp.databinding.FragmentMovieItemBinding

import com.dvg.themovieapp.movies.adapters.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MovieListAdapter(
    private val values: List<PlaceholderItem>,
    private val listener: OnMovieItemClickListener
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

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
        val item = values[position]

        holder.view.setOnClickListener {
            listener.onMovieSelected(position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val view: View = binding.root

        override fun toString(): String {
            return super.toString()
        }
    }

}

interface OnMovieItemClickListener{
    fun onMovieSelected(position: Int)
}