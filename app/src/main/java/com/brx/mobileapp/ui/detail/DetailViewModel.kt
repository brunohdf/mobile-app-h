package com.brx.mobileapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brx.mobileapp.ui.BaseViewModel
import com.brx.mobileapp.usecase.GetDetails
import com.brx.mobileapp.usecase.model.MovieModel
import io.reactivex.observers.ResourceSingleObserver

class DetailViewModel(private val useCase: GetDetails) : BaseViewModel() {

    private val details = MutableLiveData<MovieModel>()

    lateinit var movie: MovieModel

    fun details(): LiveData<MovieModel> = details

    private fun setContentEvent(detail: MovieModel) {
        details.value = detail
        setLoadingEvent(false)
    }

    fun fetchDetails(movieId: Long) {
        if (::movie.isInitialized) {
            details.value = movie
        } else {
            useCase.execute(GetDetails.Param(movieId))
                .subscribeWith(object : ResourceSingleObserver<MovieModel>() {
                    override fun onSuccess(list: MovieModel) {
                        movie = list
                        setContentEvent(list)
                    }

                    override fun onError(error: Throwable) {
                        setErrorEvent(error)
                    }
                }).also { disposable?.add(it) }
        }
    }
}