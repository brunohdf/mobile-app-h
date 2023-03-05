package com.brx.mobileapp.repository

import com.brx.mobileapp.repository.response.GenreResponse
import com.brx.mobileapp.repository.response.MovieResponse
import com.brx.mobileapp.repository.datasource.Cache
import com.brx.mobileapp.repository.datasource.TmdbApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class TMDbRepository(private val api: TmdbApi, private val cache: Cache) {

    fun getUpcomingMovies(page: Long = 1): Observable<List<MovieResponse>> {

        val movies = api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
                .map { it.results }

        return Observable.zip(movies, getGenres(), BiFunction { t1, t2 ->
            t1.map { movie ->
                movie.copy(genres = t2.filter { movie.genreIds?.contains(it.id) ?: false })
            }
        })
    }

    private fun getGenres(): Observable<List<GenreResponse>>? {
        return if (cache.genres.isEmpty()) {
            api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                    .map {
                        // save genres in cache to prevent new requests
                        cache.cacheGenres(it.genres)

                        it.genres
                    }
        } else Observable.just(cache.genres)
    }

    fun getDetails(id: Long): Observable<MovieResponse> {
        return api.movie(id, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
    }
}