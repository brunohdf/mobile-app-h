package com.brx.mobileapp.datasource.model

data class SearchResults(
    val items: List<SearchItem>
)

data class SearchItem(
    val link: String,
    val image: Image
)

data class Image(
    val thumbnailLink: String
)