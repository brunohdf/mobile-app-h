package com.brx.mobileapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected var disposable: CompositeDisposable? = CompositeDisposable()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _displayError = MutableLiveData<Throwable>()
    val displayError: LiveData<Throwable> = _displayError

    protected fun setLoading(showLoading: Boolean = true) {
        _loading.value = showLoading
    }

    protected fun setError(error: Throwable) {
        _displayError.value = error
        setLoading(false)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.clear()
        disposable = null
    }
}