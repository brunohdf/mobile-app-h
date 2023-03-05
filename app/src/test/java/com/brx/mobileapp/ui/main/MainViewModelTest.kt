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
    fun fetchMovies_shouldEnableLoading() {
        every { useCase.execute(any()) } returns Single.just(makeMovieModelList())

        val loadingEvents = mutableListOf<Boolean>()
        viewModel.loading.observeForever {
            loadingEvents.add(it)
        }

        viewModel.fetchMovies()

        Assert.assertEquals(2, loadingEvents.size)
        Assert.assertTrue(loadingEvents.first())
    }

    @Test
    fun fetchMovies_shouldDisableLoading() {
        every { useCase.execute(any()) } returns Single.just(makeMovieModelList())

        val loadingEvents = mutableListOf<Boolean>()
        viewModel.loading.observeForever {
            loadingEvents.add(it)
        }

        viewModel.fetchMovies()

        Assert.assertEquals(2, loadingEvents.size)
        Assert.assertFalse(loadingEvents.last())
    }

    @Test
    fun fetchMovies_withData_shouldTriggerMovies() {
        every { useCase.execute(any()) } returns Single.just(makeMovieModelList())

        viewModel.fetchMovies()

        Assert.assertTrue(viewModel.upcomingMovies.value?.isNotEmpty() ?: false)
    }

    @Test
    fun fetchMovies_whenEmpty_shouldTriggerMovies() {
        every { useCase.execute(any()) } returns Single.just(mutableListOf())

        viewModel.fetchMovies()

        Assert.assertTrue(viewModel.upcomingMovies.value?.isEmpty() ?: false)
    }

    @Test
    fun fetchMovies_withException_shouldTriggerError() {
        every { useCase.execute(any()) } returns Single.error(Throwable())

        viewModel.fetchMovies()

        Assert.assertTrue(viewModel.displayError.value is Throwable)
    }
}