package com.brx.mobileapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brx.mobileapp.repository.response.MovieResponse
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected var disposable: CompositeDisposable? = CompositeDisposable()
    private val displayError = MutableLiveData<Throwable>()

    private val showLoadingEvent = MutableLiveData<Boolean>()
    protected fun setLoadingEvent(showLoading: Boolean = true) {
        showLoadingEvent.value = showLoading
    }

    fun showLoading(): LiveData<Boolean> = showLoadingEvent
    fun showError(): LiveData<Throwable> = displayError

    protected fun setErrorEvent(error: Throwable) {
        displayError.value = error
        setLoadingEvent(false)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.clear()
        disposable = null
    }
}