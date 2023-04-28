package com.dvg.themovieapp.movies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.dvg.themovieapp.R
import com.dvg.themovieapp.movies.adapters.MovieListAdapter
import com.dvg.themovieapp.movies.adapters.OnMovieItemClickListener
import com.dvg.themovieapp.movies.adapters.placeholder.PlaceholderContent
import com.dvg.themovieapp.movies.viewmodels.MovieViewModel

/**
 * A fragment representing a list of Items.
 */
class MoviesFragment : Fragment(), OnMovieItemClickListener {

    private var columnCount = 1
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.moviesFragment){defaultViewModelProviderFactory}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MovieListAdapter(PlaceholderContent.ITEMS, this@MoviesFragment)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MoviesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onMovieSelected(position: Int) {
        findNavController().navigate(R.id.movieDetailsFragment)
    }
}