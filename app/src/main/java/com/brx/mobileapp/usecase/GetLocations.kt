package com.brx.mobileapp.usecase

import com.brx.mobileapp.datasource.model.Image
import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.datasource.model.SearchItem
import com.brx.mobileapp.datasource.model.SearchResults
import com.brx.mobileapp.datasource.remote.CustomSearchEngineApi
import com.brx.mobileapp.datasource.remote.LocationApi
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetLocations(
    private val searchEngineApi: CustomSearchEngineApi,
    private val locationApi: LocationApi
) : IUseCase<Unit, MutableList<Location>> {

    override fun execute(param: Unit): Single<MutableList<Location>> = locationApi.getLocations()
        .subscribeOn(Schedulers.io())
        .flatMap { list -> Observable.fromIterable(list.listLocations) }
        .flatMap(
            { item ->
                val query = "${item.type} ${item.name}"
                searchEngineApi.fetchImage(query).onErrorReturnItem(
                    SearchResults(listOf(SearchItem("", Image(""))))
                )
            },
            { location: Location, image: SearchResults? ->
                location.apply {
                    image?.items?.first()?.let {
                        imageUrl = it.link
                        imageUrlThumb = it.image.thumbnailLink
                    }
                }
            }
        ).observeOn(AndroidSchedulers.mainThread())
        .toList()
} 