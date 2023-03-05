package com.brx.mobileapp.usecase

import com.brx.mobileapp.repository.response.*
import com.brx.mobileapp.usecase.model.GenreModel
import com.brx.mobileapp.usecase.model.MovieModel
import com.brx.mobileapp.usecase.model.UpcomingMoviesModel

fun GenreResponse.toGenreModel() = GenreModel(this.id, this.name)

fun MovieResponse.toMovieModel() = MovieModel(
    this.id,
    this.title,
    this.overview,
    genres = this.genres?.map { it.toGenreModel() },
    voteAverage = (this.voteAverage?.toFloat() ?: 0f) / 2,
    genreIds = this.genreIds,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate,
)

fun UpcomingMoviesResponse.toUpcomingMoviesModel() = UpcomingMoviesModel(
    this.page,
    this.results.map {
        it.toMovieModel()
    },
    this.totalPages,
    this.totalResults
)