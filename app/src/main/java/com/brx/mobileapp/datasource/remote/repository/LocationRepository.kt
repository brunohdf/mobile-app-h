package com.brx.mobileapp.datasource.remote.repository

import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.datasource.model.Locations
import com.brx.mobileapp.datasource.remote.LocationApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocationRepository(private val api: LocationApi) {

    fun getLocations(): Observable<Locations> = api.getLocations()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getLocation(id: Int): Observable<Location> = api.getLocation(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}