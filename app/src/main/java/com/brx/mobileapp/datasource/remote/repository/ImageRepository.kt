package com.brx.mobileapp.datasource.remote.repository

import com.brx.mobileapp.datasource.model.SearchResults
import com.brx.mobileapp.datasource.remote.CustomSearchEngineApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageRepository(private val api: CustomSearchEngineApi) {

    private val searchCounter = HashMap<String, Int>()

    fun fetchImage(keyword: String): Observable<SearchResults> {

        val searchIndex = (searchCounter[keyword] ?: 0) + 1
        searchCounter[keyword] = searchIndex

        return api.fetchImage(keyword, searchIndex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
