package com.brx.mobileapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brx.mobileapp.R
import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.util.extension.loadRemoteImage
import kotlinx.android.synthetic.main.location_item.view.*

class LocationAdapter(
    private val dataSet: List<Location>,
    private val onClick: (location: Location) -> Unit
) :
    RecyclerView.Adapter<LocationAdapter.LocationHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationHolder(view, onClick)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    class LocationHolder(itemView: View, private val onClick: (location: Location) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(data: Location) {
            itemView.apply {
                name.text = data.name
                type.text = data.type
                review.rating = data.review
                review_label.text = data.review.toString()
                image.loadRemoteImage(itemView.context, data.imageUrlThumb)
            }

            itemView.setOnClickListener {
                onClick.invoke(data)
            }
        }
    }

}