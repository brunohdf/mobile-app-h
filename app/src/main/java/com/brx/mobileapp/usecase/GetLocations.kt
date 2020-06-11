package com.brx.mobileapp.usecase

import com.brx.mobileapp.datasource.model.Image
import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.datasource.model.SearchItem
import com.brx.mobileapp.datasource.model.SearchResults
import com.brx.mobileapp.datasource.remote.repository.ImageRepository
import com.brx.mobileapp.datasource.remote.repository.LocationRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetLocations(
    private val searchEngine: ImageRepository,
    private val location: LocationRepository
) : IUseCase<Unit, MutableList<Location>> {

    override fun execute(param: Unit): Single<MutableList<Location>> = location.getLocations()
        .subscribeOn(Schedulers.io())
        .flatMap { list -> Observable.fromIterable(list.listLocations) }
        .flatMap(
            { item ->
                searchEngine.fetchImage(item.type).onErrorReturnItem(
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