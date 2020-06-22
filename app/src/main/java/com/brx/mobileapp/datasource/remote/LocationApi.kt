package com.brx.mobileapp.datasource.remote

import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.datasource.model.Locations
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationApi {

    companion object {
        const val BASE_URL = "https://hotmart-mobile-app.herokuapp.com/"
    }

    @GET("locations")
    fun getLocations(): Observable<Locations>

    @GET("locations/{id}")
    fun getLocation(@Path("id") id: Int): Observable<Location>
}