package com.dvg.themovieapp.movies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import com.dvg.themovieapp.R
import com.dvg.themovieapp.databinding.FragmentMovieDetailsBinding
import com.dvg.themovieapp.movies.viewmodels.MovieViewModel

class MovieDetailsFragment : Fragment() {

    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph) { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMovieDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.movieViewModel = viewModel

        viewModel.movieDetailsLiveData.value?.name

        return binding.root
    }

}