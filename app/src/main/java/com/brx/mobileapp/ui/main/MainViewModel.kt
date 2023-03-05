package com.brx.mobileapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brx.mobileapp.ui.BaseViewModel
import com.brx.mobileapp.usecase.GetUpcomingMovies
import com.brx.mobileapp.usecase.model.MovieModel
import io.reactivex.observers.ResourceSingleObserver

class MainViewModel(private val useCase: GetUpcomingMovies) : BaseViewModel() {

    private val upcomingMovies = MutableLiveData<List<MovieModel>>()

    private lateinit var movies: List<MovieModel>

    fun upcomingMovies(): LiveData<List<MovieModel>> = upcomingMovies

    private var page: Long = 1

    private fun setContentEvent(locations: List<MovieModel>) {
        upcomingMovies.value = locations
        setLoadingEvent(false)
    }

    fun showLocations(): LiveData<List<MovieModel>> = upcomingMovies

    fun fetchLocations() {

        setLoadingEvent()

        if (::movies.isInitialized) {
            setContentEvent(movies)
        } else {
            useCase.execute(GetUpcomingMovies.Param(page))
                .subscribeWith(object : ResourceSingleObserver<MutableList<MovieModel>>() {
                    override fun onSuccess(list: MutableList<MovieModel>) {
                        movies = list
                        setContentEvent(list)
                    }

                    override fun onError(error: Throwable) {
                        setErrorEvent(error)
                    }
                }).also { disposable?.add(it) }
        }
    }
}
