package com.brx.mobileapp.datasource.remote.factory

import java.util.*

object PrimitiveFactory {

    fun randomInt(max: Int = 1000) = (0..max).random()

    fun randomFLoat(max: Int = 1000) = randomInt(max).toFloat()

    fun randomString(): String = UUID.randomUUID().toString()
}