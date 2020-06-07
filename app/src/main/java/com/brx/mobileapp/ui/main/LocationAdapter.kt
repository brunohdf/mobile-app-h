package com.brx.mobileapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brx.mobileapp.R
import com.brx.mobileapp.datasource.model.Location

class LocationAdapter(private val dataSet: List<Location>) :
    RecyclerView.Adapter<LocationAdapter.LocationHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    class LocationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Location) {
            itemView.findViewById<TextView>(R.id.name).text = data.name
            itemView.findViewById<TextView>(R.id.type).text = data.type
            itemView.findViewById<RatingBar>(R.id.review).rating = data.review
            itemView.findViewById<TextView>(R.id.review_label).text = data.review.toString()
            // TODO: glide
            itemView.findViewById<ImageView>(R.id.image)
                .setImageResource(R.drawable.ic_launcher_background)
        }
    }

}