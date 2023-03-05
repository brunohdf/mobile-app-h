package com.brx.mobileapp.usecase

import com.brx.mobileapp.factory.ModelFactory
import com.brx.mobileapp.factory.ResponseFactory
import com.brx.mobileapp.repository.TMDbRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.reactivex.Observable
import org.junit.After
import org.junit.Before

import org.junit.Test

class GetLocationsTest() {

    private val repository: TMDbRepository = mockk()
    private lateinit var useCase: GetUpcomingMovies

    @Before
    fun setUp() {
        useCase = GetUpcomingMovies(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getLocations_shouldFetchOneImage_forEachLocation() {
        every { repository.getUpcomingMovies() } returns Observable.just(
            ResponseFactory.makeMovieResponseList(
                2
            )
        )
        useCase.execute(GetUpcomingMovies.Param(1))
            .test()
            .assertComplete()

        verify(exactly = 1) { repository.getUpcomingMovies(any()) }
    }

    @Test
    fun getLocations_withFetchImageError_shouldComplete() {
        every { repository.getUpcomingMovies() } returns Observable.just(
            ResponseFactory.makeMovieResponseList(1)
        )
        useCase.execute(GetUpcomingMovies.Param(1))
            .test()
            .assertComplete()
    }

    @Test
    fun getLocationsError_shouldNotComplete() {
        every { repository.getUpcomingMovies() } returns Observable.error(Throwable())

        useCase.execute(GetUpcomingMovies.Param(1))
            .test()
            .assertNotComplete()
    }
}