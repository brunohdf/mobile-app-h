package com.brx.mobileapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brx.mobileapp.R
import com.brx.mobileapp.usecase.model.MovieModel
import org.koin.android.ext.android.inject

class DetailFragment : Fragment() {

    companion object {
        private const val ID = "id"
        private const val IMAGE = "image"
    }

    private val viewModel: DetailViewModel by inject()

    private val movies = mutableListOf<MovieModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //image.loadRemoteImage(requireContext(), requireArguments().getString(IMAGE) ?: "")
    }

}