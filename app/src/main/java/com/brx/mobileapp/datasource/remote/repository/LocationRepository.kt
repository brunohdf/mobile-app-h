package com.brx.mobileapp.datasource.remote.repository

import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.datasource.model.Locations
import com.brx.mobileapp.datasource.remote.LocationApi
import io.reactivex.Observable

class LocationRepository(private val api: LocationApi) {

    fun getLocations(): Observable<Locations> = api.getLocations()

    fun getLocation(id: Int): Observable<Location> = api.getLocation(id)
}