package com.brx.mobileapp.datasource.model

data class Locations(
    val listLocations: List<Location>
)

data class Location(
    val id: Int,
    val name: String,
    val review: Float,
    val type: String,
    val about: String? = null,
    val phone: String? = null,
    val schedule: List<Map<String, Operation>>? = null
)

data class Operation(
    val open: String,
    val close: String
)