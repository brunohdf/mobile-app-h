package com.brx.mobileapp.datasource.remote.repository

import com.brx.mobileapp.datasource.model.SearchResults
import com.brx.mobileapp.datasource.remote.CustomSearchEngineApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageRepository(private val api: CustomSearchEngineApi) {

    fun fetchImage(keywork: String): Observable<SearchResults> = api.fetchImage(keywork)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
