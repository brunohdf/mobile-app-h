package com.brx.mobileapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brx.mobileapp.ui.BaseViewModel
import com.brx.mobileapp.usecase.GetDetails
import com.brx.mobileapp.usecase.model.MovieModel
import io.reactivex.observers.ResourceSingleObserver

class DetailViewModel(
    private val useCase: GetDetails,
    private val movieId: Long
) : BaseViewModel() {

    private val _details = MutableLiveData<MovieModel>()
    val details: LiveData<MovieModel> = _details

    lateinit var movie: MovieModel

    fun fetchDetails() {
        showLoading()
        if (::movie.isInitialized) {
            _details.value = movie
        } else {
            useCase.execute(GetDetails.Param(movieId))
                .subscribeWith(object : ResourceSingleObserver<MovieModel>() {
                    override fun onSuccess(movie: MovieModel) {
                        this@DetailViewModel.movie = movie
                        _details.value = movie
                        showLoading(false)
                    }

                    override fun onError(error: Throwable) {
                        showError(error)
                        showLoading(false)
                    }
                }).also { disposable?.add(it) }
        }
    }
}