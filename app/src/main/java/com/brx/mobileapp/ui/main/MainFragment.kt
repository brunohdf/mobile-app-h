package com.brx.mobileapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.brx.mobileapp.R
import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.util.extension.visible
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by inject()

    private val dataSet = mutableListOf<Location>()

    private val adapter = LocationAdapter(dataSet)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindEvents()

        viewModel.fetchLocations()
    }

    private fun bindEvents() {
        viewModel.showLocations().observe(viewLifecycleOwner, Observer {
            progressBar.visible(false)
            dataSet.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.showLoading().observe(viewLifecycleOwner, Observer {
            progressBar.visible(it)
        })

        viewModel.showError().observe(viewLifecycleOwner, Observer {
            progressBar.visible(false)
            Toast.makeText(requireContext(), "Ops: ${it.message}", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locations.adapter = adapter
        locations.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

    }

}
