package com.brx.mobileapp.factory

import com.brx.mobileapp.datasource.model.Image
import com.brx.mobileapp.datasource.model.SearchItem
import com.brx.mobileapp.datasource.model.SearchResults

object SearchResultFactory {

    fun makeSearchResults() = SearchResults(
        listOf(
            SearchItem(PrimitiveFactory.randomString(), Image(PrimitiveFactory.randomString()))
        )
    )
}