package com.brx.mobileapp.usecase

import com.brx.mobileapp.datasource.remote.CustomSearchEngineApi
import com.brx.mobileapp.datasource.remote.LocationApi
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

    private val locationsApi: LocationApi = mockk()
    private val searchApi: CustomSearchEngineApi = mockk()
    private lateinit var useCase: GetLocations

    @Before
    fun setUp() {
        useCase = GetLocations(searchApi, locationsApi)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getLocations_shouldFetchOneImage_forEachLocation() {
        every { locationsApi.getLocations() } returns Observable.just(
            LocationFactory.makeLocations(
                2
            )
        )
        every { searchApi.fetchImage(any()) } returns Observable.just(SearchResultFactory.makeSearchResults())

        useCase.execute(Unit)
            .test()
            .assertComplete()

        verify(exactly = 1) { locationsApi.getLocations() }
        verify(exactly = 2) { searchApi.fetchImage(any()) }
    }

    @Test
    fun getLocations_withFetchImageError_shouldComplete() {
        every { locationsApi.getLocations() } returns Observable.just(
            LocationFactory.makeLocations(
                1
            )
        )
        every { searchApi.fetchImage(any()) } returns Observable.error(Throwable())

        useCase.execute(Unit)
            .test()
            .assertComplete()
    }

    @Test
    fun getLocationsError_shouldNotComplete() {
        every { locationsApi.getLocations() } returns Observable.error(Throwable())
        every { searchApi.fetchImage(any()) } returns Observable.just(SearchResultFactory.makeSearchResults())

        useCase.execute(Unit)
            .test()
            .assertNotComplete()
    }
}