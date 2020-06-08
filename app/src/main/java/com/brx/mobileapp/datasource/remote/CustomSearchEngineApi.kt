package com.brx.mobileapp.datasource.remote

import com.brx.mobileapp.BuildConfig
import com.brx.mobileapp.datasource.model.SearchResults
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CustomSearchEngineApi {

    companion object {
        const val BASE_URL = "https://www.googleapis.com/"
    }

    @Suppress("SpellCheckingInspection")
    @GET("customsearch/v1?num=1&searchType=image")
    fun fetchImage(
        @Query("q") query: String,
        @Query("key") apiKey: String = BuildConfig.KEY,
        @Query("cx") language: String = BuildConfig.CX
    ): Observable<SearchResults>
}