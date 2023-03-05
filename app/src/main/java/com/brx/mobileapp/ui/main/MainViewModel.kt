package com.brx.mobileapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brx.mobileapp.ui.BaseViewModel
import com.brx.mobileapp.usecase.GetUpcomingMovies
import com.brx.mobileapp.usecase.model.MovieModel
import io.reactivex.observers.ResourceSingleObserver

class MainViewModel(private val useCase: GetUpcomingMovies) : BaseViewModel() {

    private val _upcomingMovies = MutableLiveData<List<MovieModel>>()
    val upcomingMovies: LiveData<List<MovieModel>> = _upcomingMovies

    private lateinit var movies: List<MovieModel>

    fun fetchMovies(page: Long = 1) {
        showLoading()

        if (::movies.isInitialized) {
            _upcomingMovies.value = movies
            showLoading(false)
        } else {
            useCase.execute(GetUpcomingMovies.Param(page))
                .subscribeWith(object : ResourceSingleObserver<MutableList<MovieModel>>() {
                    override fun onSuccess(list: MutableList<MovieModel>) {
                        movies = list
                        _upcomingMovies.value = list
                        showLoading(false)
                    }

                    override fun onError(error: Throwable) {
                        showError(error)
                        showLoading(false)
                    }
                }).also {
                    disposable?.add(it)
                }
        }
    }
}
