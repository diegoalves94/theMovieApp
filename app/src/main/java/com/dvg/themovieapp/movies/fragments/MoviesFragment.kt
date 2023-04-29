package com.dvg.themovieapp.movies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.dvg.themovieapp.R
import com.dvg.themovieapp.databinding.FragmentMoviesBinding
import com.dvg.themovieapp.movies.adapters.MovieListAdapter
import com.dvg.themovieapp.movies.adapters.OnMovieItemClickListener
import com.dvg.themovieapp.movies.viewmodels.MovieViewModel

class MoviesFragment : Fragment(), OnMovieItemClickListener {

    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.moviesFragment) { defaultViewModelProviderFactory }
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMoviesBinding.inflate(inflater)
        val view = binding.root as RecyclerView

        adapter = MovieListAdapter(this@MoviesFragment)

        view.apply {
            this.adapter = this@MoviesFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        initObservers()
        return view
    }

    private fun initObservers() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateData(it)
            }
        })

        viewModel.navigationToDetailsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment()
                findNavController().navigate(action)
            }
        })
    }


    override fun onMovieSelected(position: Int) {
        viewModel.onMovieSelected(position)
    }
}