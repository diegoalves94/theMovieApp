package com.dvg.themovieapp.movies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvg.themovieapp.R
import com.dvg.themovieapp.databinding.FragmentMoviesBinding
import com.dvg.themovieapp.movies.ui.adapters.MovieListAdapter
import com.dvg.themovieapp.movies.ui.adapters.OnMovieItemClickListener
import com.dvg.themovieapp.movies.ui.viewmodels.MovieViewModel

class MoviesFragment : Fragment(), OnMovieItemClickListener {

    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph) {
        defaultViewModelProviderFactory
    }
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMoviesBinding.inflate(inflater)
        val view = binding.root
        val moviesList = binding.list

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = MovieListAdapter(this@MoviesFragment)
        moviesList.apply {
            this.adapter = this@MoviesFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        initObservers()

        return view
    }

    private fun initObservers() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.updateData(it)
            }
        }
    }


    override fun onMovieSelected(position: Int) {
        findNavController().navigate(
            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                viewModel.movieListLiveData.value?.get(position)?.getMovieId()!!
            )
        )
    }
}