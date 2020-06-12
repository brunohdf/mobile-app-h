package com.brx.mobileapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.brx.mobileapp.R
import com.brx.mobileapp.util.extension.loadRemoteImage
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {

    companion object {
        private const val ID = "id"
        private const val IMAGE = "image"

        fun newInstance(id: Int, image: String): DetailFragment {
            return DetailFragment().apply {
                arguments = bundleOf(ID to id, IMAGE to image)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image.loadRemoteImage(requireContext(), requireArguments().getString(IMAGE) ?: "")
    }

}