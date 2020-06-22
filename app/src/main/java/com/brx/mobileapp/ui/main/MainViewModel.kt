package com.brx.mobileapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.ui.BaseViewModel
import com.brx.mobileapp.usecase.GetLocations
import io.reactivex.observers.ResourceSingleObserver

class MainViewModel(private val useCase: GetLocations) : BaseViewModel() {

    private lateinit var locations: List<Location>
    private val locationsEvent = MutableLiveData<List<Location>>()
    private val displayError = MutableLiveData<Throwable>()

    private fun setErrorEvent(error: Throwable) {
        displayError.value = error
        setLoadingEvent(false)
    }

    private fun setContentEvent(locations: List<Location>) {
        locationsEvent.value = locations
        setLoadingEvent(false)
    }

    fun showLocations(): LiveData<List<Location>> = locationsEvent
    fun showError(): LiveData<Throwable> = displayError

    fun fetchLocations() {

        setLoadingEvent()

        if (::locations.isInitialized) {
            setContentEvent(locations)
        } else {
            useCase.execute(Unit)
                .subscribeWith(object : ResourceSingleObserver<MutableList<Location>>() {
                    override fun onSuccess(list: MutableList<Location>) {
                        locations = list
                        setContentEvent(list)
                    }

                    override fun onError(error: Throwable) {
                        setErrorEvent(error)
                    }
                }).also { disposable?.add(it) }
        }
    }
}
