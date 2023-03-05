package com.brx.mobileapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brx.mobileapp.R
import com.brx.mobileapp.usecase.model.MovieModel
import com.brx.mobileapp.util.MovieImageUrlBuilder
import com.brx.mobileapp.util.extension.loadRemoteImage
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private val dataSet: List<MovieModel>,
    private val onClick: (location: MovieModel) -> Unit
) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view, onClick)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    class MoviesViewHolder(itemView: View, private val onClick: (movie: MovieModel) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val movieImageUrlBuilder = MovieImageUrlBuilder()

        fun bind(movie: MovieModel) {
            itemView.tvTitle.text = movie.title
            itemView.tvGenre.text = movie.genres?.first()?.name.orEmpty()
            with(movie.vote_average) {
                itemView.rbReview.rating = this
                itemView.tvReviewLabel.text = this.toString()
            }
            itemView.cvMovie.setOnClickListener {
                onClick.invoke(movie)
            }

            movie.posterPath?.let {
                itemView.ivPoster.loadRemoteImage(itemView.context, movieImageUrlBuilder.buildPosterUrl(it))
            }
        }
    }

}