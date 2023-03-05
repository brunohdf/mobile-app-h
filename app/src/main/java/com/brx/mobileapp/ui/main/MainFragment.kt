package com.brx.mobileapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.brx.mobileapp.R
import com.brx.mobileapp.usecase.model.MovieModel
import com.brx.mobileapp.util.extension.visible
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by inject()

    private val movies = mutableListOf<MovieModel>()

    private val moviesAdapter = MoviesAdapter(movies) { movie ->
        findNavController().navigate(R.id.detail)
        // bundleOf(LOCATION_KEY to location)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMovies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvents()
        bindView()
    }

    private fun bindEvents() {
        viewModel.upcomingMovies.observe(viewLifecycleOwner, Observer {
            rvMovies.visible()
            movies.addAll(it)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            pbLoading.visible(it)
        })

        viewModel.displayError.observe(viewLifecycleOwner, Observer {
            evError.apply {
                visible()
                title = context.getString(R.string.error)
                message = context.getString(R.string.unexpected_error)
                image = R.drawable.ic_error

            }
        })
    }

    private fun bindView() {
        with(rvMovies) {
            StaggeredGridLayoutManager(
                resources.getInteger(R.integer.grid_columns), RecyclerView.VERTICAL
            ).let {
                adapter = moviesAdapter
                layoutManager = it
            }
        }
    }
}
