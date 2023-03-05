package com.brx.mobileapp.repository.response

import com.squareup.moshi.Json

data class UpcomingMoviesResponse(
    val page: Int,
    val results: List<MovieResponse>,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int
)

data class MovieResponse(
    val id: Long,
    val title: String,
    val overview: String?,
    val genres: List<GenreResponse>?,
    @field:Json(name = "vote_average") val voteAverage: String?,
    @field:Json(name = "genre_ids") val genreIds: List<Int>?,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "release_date") val releaseDate: String?
)