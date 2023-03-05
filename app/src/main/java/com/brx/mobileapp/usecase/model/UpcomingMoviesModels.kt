package com.brx.mobileapp.usecase.model

data class UpcomingMoviesModel(
    val page: Int,
    val results: List<MovieModel>,
    val totalPages: Int,
    val totalResults: Int
)

data class MovieModel(
    val id: Long,
    val title: String,
    val overview: String?,
    val genres: List<GenreModel>?,
    val voteAverage: Float,
    val genreIds: List<Int>?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?
)