package com.brx.mobileapp.factory

import com.brx.mobileapp.factory.PrimitiveFactory.randomFLoat
import com.brx.mobileapp.factory.PrimitiveFactory.randomInt
import com.brx.mobileapp.factory.PrimitiveFactory.randomString
import com.brx.mobileapp.repository.response.GenreResponse
import com.brx.mobileapp.repository.response.GenresResponse
import com.brx.mobileapp.repository.response.MovieResponse
import com.brx.mobileapp.repository.response.UpcomingMoviesResponse

object ResponseFactory {

    private val genreResponse = GenreResponse(randomInt(), randomString())

    fun makeMovieResponseList(number: Int = 3): MutableList<MovieResponse> =
        mutableListOf<MovieResponse>().apply {
            for (i in 1..number) this.add(makeMovieResponse())
        }

    fun makeUpcomingMoviesResponse(totalPages: Int = 1, totalResults: Int = 10) = UpcomingMoviesResponse(
        randomInt(),
        makeMovieResponseList(totalResults / totalPages),
        totalResults,
        totalPages
    )

    fun makeEmptyUpcomingMoviesResponse() = UpcomingMoviesResponse(0, makeMovieResponseList(0), 0, 0)

    fun makeMovieResponse(): MovieResponse {

        val genres = makeGenreListResponse()
        val genresIds = genres.map { it.id }

        return MovieResponse(
            id = randomInt().toLong(),
            title = randomString(),
            overview = randomString(),
            genres = genres,
            vote_average = randomFLoat(5).toString(),
            genreIds = genresIds,
            posterPath = randomString(),
            backdropPath = randomString(),
            releaseDate = randomString()
        )
    }

    fun makeGenreListResponse(): List<GenreResponse> {
        return listOf(genreResponse)
    }

    private fun makeGenresResponse() = GenresResponse(makeGenreListResponse())

    fun makeGenresResponseFromCache(): GenresResponse {
        var cachedGenres = makeGenresResponse()
        cachedGenres = cachedGenres.copy(genres = cachedGenres.genres.map { it.copy(name = GENRE_FROM_CACHE) })
        return cachedGenres
    }

    fun makeGenresResponseFromRemoteApi(): GenresResponse {
        var cachedGenres = makeGenresResponse()
        cachedGenres = cachedGenres.copy(genres = cachedGenres.genres.map { it.copy(name = GENRE_FROM_REMOTE) })
        return cachedGenres
    }

    const val GENRE_FROM_REMOTE = "GENRE FROM REMOTE"
    const val GENRE_FROM_CACHE = "GENRE FROM CACHE"
}