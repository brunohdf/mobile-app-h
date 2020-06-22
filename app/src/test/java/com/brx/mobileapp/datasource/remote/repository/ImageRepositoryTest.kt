package com.brx.mobileapp.datasource.remote.repository

import com.brx.mobileapp.datasource.remote.CustomSearchEngineApi
import com.brx.mobileapp.factory.PrimitiveFactory.randomString
import com.brx.mobileapp.factory.SearchResultFactory.makeSearchResults
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
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
        every { api.fetchImage(any(), any()) } returns Observable.just(makeSearchResults())

        repository.fetchImage(randomString())
            .test()
            .assertValue {
                it.items.isNotEmpty()
            }
    }

    @Test
    fun fetchImage_withNoData_shouldCompleteWithoutErrors() {
        every { api.fetchImage(any(), any()) } returns Observable.empty()

        repository.fetchImage(randomString())
            .test()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun fetchImage_withError_shouldReturnException() {
        every { api.fetchImage(any(), any()) } returns Observable.error(Throwable())

        repository.fetchImage(randomString())
            .test()
            .assertError(Throwable::class.java)
    }

    @Test
    fun fetchImageTwoTimes_withSameKeyword_shouldIncrementSearchIndex() {
        every { api.fetchImage(any(), any()) } returns Observable.just(makeSearchResults())
        val query = randomString()

        repository.fetchImage(query).test()
        repository.fetchImage(query).test()

        verify(exactly = 2) { api.fetchImage(any(), any()) }
        verify(exactly = 1) { api.fetchImage(any(), 1) }
        verify(exactly = 1) { api.fetchImage(any(), 2) }
    }

    @Test
    fun fetchImageTwoTimes_withDifferentKeywords_shouldNotIncrementSearchIndex() {
        every { api.fetchImage(any(), any()) } returns Observable.just(makeSearchResults())
        val query = randomString()

        repository.fetchImage(query).test()
        repository.fetchImage(query.reversed())
            .test() // just for avoid generate two random queries equals..

        verify(exactly = 2) { api.fetchImage(any(), 1) }
    }
}