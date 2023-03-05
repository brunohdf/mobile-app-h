@file:Suppress("SpellCheckingInspection")

package com.brx.mobileapp.repository.datasource

import com.brx.mobileapp.repository.response.GenreResponse


class Cache {

    var genres = listOf<GenreResponse>()

    fun cacheGenres(genres: List<GenreResponse>) {
        this.genres = genres
    }
}