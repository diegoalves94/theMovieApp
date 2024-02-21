package com.dvg.themovieapp.movies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.dvg.themovieapp.R
import com.dvg.themovieapp.databinding.FragmentMovieDetailsBinding
import com.dvg.themovieapp.movies.ui.viewmodels.MovieDetailsViewModel

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel by navGraphViewModels<MovieDetailsViewModel>(R.id.movie_graph) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.movieDetailsViewModel = viewModel
        binding.icMovieImages.registerLifecycle(lifecycle)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieDetails()
    }

    private fun getMovieDetails() {
        viewModel.setMovieData(args.argMovieId)
        viewModel.setMovieImageCarousel()
    }
}