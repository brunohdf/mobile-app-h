package com.brx.mobileapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.brx.mobileapp.R
import com.brx.mobileapp.usecase.model.MovieModel
import com.brx.mobileapp.util.extension.visible
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by inject()

    private val movies = mutableListOf<MovieModel>()

    private val adapter = LocationAdapter(movies) { movie ->
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
        viewModel.fetchLocations()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvents()
        bindView()

    }

    private fun bindEvents() {
        viewModel.showLocations().observe(viewLifecycleOwner, Observer {
            locations.visible()
            movies.addAll(it)
        })

        viewModel.showLoading().observe(viewLifecycleOwner, Observer {
            progressBar.visible(it)
        })

        viewModel.showError().observe(viewLifecycleOwner, Observer {
            errorView.apply {
                visible()
                title = context.getString(R.string.error)
                message = context.getString(R.string.unexpected_error)
                image = R.drawable.ic_error

            }
        })
    }

    private fun bindView() {
        locations.adapter = adapter
        locations.layoutManager = StaggeredGridLayoutManager(
            resources.getInteger(R.integer.grid_columns), VERTICAL
        )
    }
}
