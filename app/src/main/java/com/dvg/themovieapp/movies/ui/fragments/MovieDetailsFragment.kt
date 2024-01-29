package com.dvg.themovieapp.movies.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.dvg.themovieapp.R
import com.dvg.themovieapp.databinding.FragmentMovieDetailsBinding
import com.dvg.themovieapp.movies.ui.viewmodels.MovieDetailsViewModel
import com.dvg.themovieapp.movies.utils.Constants
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieDetailsFromApi()
        getMovieImagesFromApi()
    }

    private fun getMovieDetailsFromApi() {
        viewModel.setMovieData(args.argMovieId)
        viewModel.movieDetailsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                setMoviePosterImage(it.getPosterImagePath())
            }
        })
    }

    private fun setMoviePosterImage(fullImagePath: String) {
        Glide.with(requireContext())
            .load(fullImagePath)
            .placeholder(R.drawable.placeholder)
            .into(binding.ivMoviePoster)
    }

    private fun getMovieImagesFromApi() {
        viewModel.movieImagesLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {backdrops ->
                binding.icMovieImages.registerLifecycle(lifecycle)

                val imageList = mutableListOf<CarouselItem>()
                for (backdrop in backdrops){
                    imageList.add(
                        CarouselItem(
                            imageUrl = "${Constants.IMAGE_BASE_URL}${backdrop.file_path}"
                        )
                    )
                }
                binding.icMovieImages.setData(imageList)
            }
        })
    }

}