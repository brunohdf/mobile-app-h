package com.brx.mobileapp.repository

import com.brx.mobileapp.factory.ResponseFactory.GENRE_FROM_CACHE
import com.brx.mobileapp.factory.ResponseFactory.GENRE_FROM_REMOTE
import com.brx.mobileapp.factory.ResponseFactory.makeEmptyUpcomingMoviesResponse
import com.brx.mobileapp.factory.ResponseFactory.makeGenreListResponse
import com.brx.mobileapp.factory.ResponseFactory.makeGenresResponseFromCache
import com.brx.mobileapp.factory.ResponseFactory.makeGenresResponseFromRemoteApi
import com.brx.mobileapp.factory.ResponseFactory.makeUpcomingMoviesResponse
import com.brx.mobileapp.repository.datasource.Cache
import com.brx.mobileapp.repository.datasource.TmdbApi
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class TMDbRepositoryTest {
    private lateinit var api: TmdbApi
    private lateinit var cache: Cache
    private lateinit var repository: TMDbRepository

    @Before
    fun setup() {
        api = mockk()
        cache = mockk()
        every { cache.genres } returns makeGenreListResponse()
        every { cache.cacheGenres(any()) } just Runs
        repository = TMDbRepository(api, cache)

        // specify which thread to use for IO and Main schedulers
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun getUpcomingMovies_withDataOnApi_returnsData() {
        every { api.upcomingMovies(any(), any(), any(), any()) } returns Observable.just(makeUpcomingMoviesResponse())

        repository.getUpcomingMovies()
            .test()
            .assertValue { it.isNotEmpty() }
    }

    @Test
    fun getUpcomingMovies_noDataOnApi_returnsEmpty() {
        every { api.upcomingMovies(any(), any(), any(), any()) } returns Observable.just(makeEmptyUpcomingMoviesResponse())

        repository.getUpcomingMovies()
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun getUpcomingMovies_withoutCachedGenres_returnsGenresFromRemote() {
        val cachedGenres = makeGenresResponseFromRemoteApi()

        every { api.upcomingMovies(any(), any(), any(), any()) } returns Observable.just(makeUpcomingMoviesResponse())
        every { api.genres(any(), any()) } returns Observable.just(cachedGenres)
        every { cache.genres } returns emptyList()

        repository.getUpcomingMovies()
            .test()
            .assertValue {
                it.first().genres?.get(0)?.name == GENRE_FROM_REMOTE
            }
    }

    @Test
    fun getUpcomingMovies_withCachedGenres_returnsCachedGenres() {
        val cachedGenres = makeGenresResponseFromCache()

        every { api.upcomingMovies(any(), any(), any(), any()) } returns Observable.just(makeUpcomingMoviesResponse())
        every { api.genres(any(), any()) } returns Observable.just(cachedGenres)
        every { cache.genres } returns cachedGenres.genres

        repository.getUpcomingMovies()
            .test()
            .assertValue {
                it.first().genres?.get(0)?.name == GENRE_FROM_CACHE
            }
    }
}