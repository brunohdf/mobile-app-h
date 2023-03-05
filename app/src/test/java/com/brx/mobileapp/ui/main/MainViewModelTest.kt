package com.brx.mobileapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.brx.mobileapp.factory.ModelFactory.makeMovieModelList
import com.brx.mobileapp.usecase.GetUpcomingMovies
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.reactivex.Single
import org.junit.*

class MainViewModelTest {

    private val useCase: GetUpcomingMovies = mockk()
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel(useCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun fetchLocations_shouldEnableLoading() {
        every { useCase.execute(any()) } returns Single.just(makeMovieModelList())

        val loadingEvents = mutableListOf<Boolean>()
        viewModel.showLoading().observeForever {
            loadingEvents.add(it)
        }

        viewModel.fetchLocations()

        Assert.assertEquals(2, loadingEvents.size)
        Assert.assertTrue(loadingEvents.first())
    }

    @Test
    fun fetchLocations_shouldDisableLoading() {
        every { useCase.execute(any()) } returns Single.just(makeMovieModelList())

        val loadingEvents = mutableListOf<Boolean>()
        viewModel.showLoading().observeForever {
            loadingEvents.add(it)
        }

        viewModel.fetchLocations()

        Assert.assertEquals(2, loadingEvents.size)
        Assert.assertFalse(loadingEvents.last())
    }

    @Test
    fun fetchLocations_withData_shouldTriggerLocations() {
        every { useCase.execute(any()) } returns Single.just(makeMovieModelList())

        viewModel.fetchLocations()

        Assert.assertTrue(viewModel.showLocations().value?.isNotEmpty() ?: false)
    }

    @Test
    fun fetchLocations_whenEmpty_shouldTriggerLocations() {
        every { useCase.execute(any()) } returns Single.just(mutableListOf())

        viewModel.fetchLocations()

        Assert.assertTrue(viewModel.showLocations().value?.isEmpty() ?: false)
    }

    @Test
    fun fetchLocations_withException_shouldTriggerError() {
        every { useCase.execute(any()) } returns Single.error(Throwable())

        viewModel.fetchLocations()

        Assert.assertTrue(viewModel.showError().value is Throwable)
    }
}