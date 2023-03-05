package com.brx.mobileapp.usecase.model

import com.squareup.moshi.Json

data class UpcomingMoviesModel(
    val page: Int,
    val results: List<MovieModel>,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int
)

data class MovieModel(
    val id: Long,
    val title: String,
    val overview: String?,
    val genres: List<GenreModel>?,
    val vote_average: Float,
    @field:Json(name = "genre_ids") val genreIds: List<Int>?,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "release_date") val releaseDate: String?
)