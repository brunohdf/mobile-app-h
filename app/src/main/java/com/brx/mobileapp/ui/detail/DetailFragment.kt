package com.brx.mobileapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brx.mobileapp.R
import com.brx.mobileapp.usecase.model.MovieModel
import com.brx.mobileapp.util.Constants.MOVIE_KEY
import com.brx.mobileapp.util.MovieImageUrlBuilder
import com.brx.mobileapp.util.extension.loadRemoteImage
import com.brx.mobileapp.util.extension.visible
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    companion object {
        private const val ID = "id"
        private const val IMAGE = "image"
    }

    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    private val viewModel: DetailViewModel by inject {
        parametersOf(arguments?.getLong(MOVIE_KEY))
    }

    private val movies = mutableListOf<MovieModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvents()
    }

    private fun bindEvents() {
        with(viewModel) {
            details.observe(viewLifecycleOwner) {
                tvTitle.text = it.title
                tvSummary.text = it.overview
                rbReview.rating = it.voteAverage
                tbLabelReview.text = it.voteAverage.toString()
                ivPoster.loadRemoteImage(requireContext(), it.posterPath)
                it.backdropPath?.let { img ->
                    ivPoster.loadRemoteImage(
                        requireContext(),
                        movieImageUrlBuilder.buildPosterUrl(img)
                    )
                }
            }
            viewModel.loading.observe(viewLifecycleOwner) {
                pbLoading.visible(it)
            }

            viewModel.displayError.observe(viewLifecycleOwner) {
                evError.apply {
                    visible()
                    title = context.getString(R.string.error)
                    message = context.getString(R.string.unexpected_error)
                    image = R.drawable.ic_error

                }
            }
        }
    }

}