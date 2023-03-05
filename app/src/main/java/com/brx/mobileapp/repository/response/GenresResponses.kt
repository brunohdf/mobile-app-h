package com.brx.mobileapp.repository.response

data class GenresResponse(val genres: List<GenreResponse>)

data class GenreResponse(val id: Int, val name: String)