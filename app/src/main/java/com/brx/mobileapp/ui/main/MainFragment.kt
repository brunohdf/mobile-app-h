package com.brx.mobileapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.brx.mobileapp.R
import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.util.MarginItemDecoration
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val dataSet = mutableListOf<Location>().apply {
        // TODO: remove when integrate ViewModel
        repeat(10) {
            add(Location(it, "location name $it", (it.toFloat() / 2), "type"))
        }
    }

    private val adapter = LocationAdapter(dataSet)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locations.adapter = adapter
        locations.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        locations.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.grid_spacing).toInt()
            )
        )

    }

}
