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

    private fun setContentEvent(movies: List<MovieModel>) {
        _upcomingMovies.value = movies
        setLoading(false)
    }

    fun fetchMovies(page: Long = 1) {

        setLoading()

        if (::movies.isInitialized) {
            _upcomingMovies.value = movies
            setLoading(false)
        } else {
            useCase.execute(GetUpcomingMovies.Param(page))
                .subscribeWith(object : ResourceSingleObserver<MutableList<MovieModel>>() {
                    override fun onSuccess(list: MutableList<MovieModel>) {
                        movies = list
                        setContentEvent(list)
                    }

                    override fun onError(error: Throwable) {
                        setError(error)
                    }
                }).also {
                    disposable?.add(it)
                }
        }
    }
}
