package com.brx.mobileapp.factory

import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.datasource.model.Locations
import com.brx.mobileapp.factory.PrimitiveFactory.randomFLoat
import com.brx.mobileapp.factory.PrimitiveFactory.randomInt
import com.brx.mobileapp.factory.PrimitiveFactory.randomString

object LocationFactory {

    fun makeLocation() = Location(
        randomInt(),
        randomString(),
        randomFLoat(),
        randomString(),
        randomString(),
        randomString()
    )

    fun makeLocations(number: Int = 3) = Locations(
        mutableListOf<Location>().apply {
            for (i in 1..number) this.add(makeLocation())
        }
    )
}