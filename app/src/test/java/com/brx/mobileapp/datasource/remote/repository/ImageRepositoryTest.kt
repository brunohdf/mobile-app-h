package com.brx.mobileapp.datasource.remote.repository

import com.brx.mobileapp.datasource.remote.CustomSearchEngineApi
import com.brx.mobileapp.datasource.remote.factory.LocationFactory.makeSearchResults
import com.brx.mobileapp.datasource.remote.factory.PrimitiveFactory.randomString
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test

class ImageRepositoryTest : RepositoryTest() {

    private lateinit var api: CustomSearchEngineApi
    private lateinit var repository: ImageRepository

    @Before
    fun setUp() {
        api = mockk()
        repository = ImageRepository(api)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun fetchImage_withRemoteData_shouldReturnLocations() {
        every { api.fetchImage(any()) } returns Observable.just(makeSearchResults())

        repository.fetchImage(randomString())
            .test()
            .assertValue {
                it.items.isNotEmpty()
            }
    }

    @Test
    fun fetchImage_withNoData_shouldCompleteWithoutErrors() {
        every { api.fetchImage(any()) } returns Observable.empty()

        repository.fetchImage(randomString())
            .test()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun fetchImage_withError_shouldReturnException() {
        every { api.fetchImage(any()) } returns Observable.error(Throwable())

        repository.fetchImage(randomString())
            .test()
            .assertError(Throwable::class.java)
    }
}