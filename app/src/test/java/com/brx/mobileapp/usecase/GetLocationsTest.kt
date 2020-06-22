package com.brx.mobileapp.usecase

import com.brx.mobileapp.datasource.remote.repository.ImageRepository
import com.brx.mobileapp.datasource.remote.repository.LocationRepository
import com.brx.mobileapp.datasource.remote.repository.RepositoryTest
import com.brx.mobileapp.factory.LocationFactory
import com.brx.mobileapp.factory.SearchResultFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.reactivex.Observable
import org.junit.After
import org.junit.Before

import org.junit.Test

class GetLocationsTest : RepositoryTest() {

    private val locationsRepository: LocationRepository = mockk()
    private val searchRepository: ImageRepository = mockk()
    private lateinit var useCase: GetLocations

    @Before
    fun setUp() {
        useCase = GetLocations(searchRepository, locationsRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getLocations_shouldFetchOneImage_forEachLocation() {
        every { locationsRepository.getLocations() } returns Observable.just(
            LocationFactory.makeLocations(
                2
            )
        )
        every { searchRepository.fetchImage(any()) } returns Observable.just(SearchResultFactory.makeSearchResults())

        useCase.execute(Unit)
            .test()
            .assertComplete()

        verify(exactly = 1) { locationsRepository.getLocations() }
        verify(exactly = 2) { searchRepository.fetchImage(any()) }
    }

    @Test
    fun getLocations_withFetchImageError_shouldComplete() {
        every { locationsRepository.getLocations() } returns Observable.just(
            LocationFactory.makeLocations(
                1
            )
        )
        every { searchRepository.fetchImage(any()) } returns Observable.error(Throwable())

        useCase.execute(Unit)
            .test()
            .assertComplete()
    }

    @Test
    fun getLocationsError_shouldNotComplete() {
        every { locationsRepository.getLocations() } returns Observable.error(Throwable())
        every { searchRepository.fetchImage(any()) } returns Observable.just(SearchResultFactory.makeSearchResults())

        useCase.execute(Unit)
            .test()
            .assertNotComplete()
    }
}