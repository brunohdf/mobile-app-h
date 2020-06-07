package com.brx.mobileapp.datasource.remote.factory

import com.brx.mobileapp.datasource.model.Location
import com.brx.mobileapp.datasource.model.Locations
import com.brx.mobileapp.datasource.model.Operation
import com.brx.mobileapp.datasource.remote.factory.PrimitiveFactory.randomFLoat
import com.brx.mobileapp.datasource.remote.factory.PrimitiveFactory.randomInt
import com.brx.mobileapp.datasource.remote.factory.PrimitiveFactory.randomString

object LocationFactory {

    fun makeLocation() = Location(
        randomInt(),
        randomString(),
        randomFLoat(),
        randomString(),
        randomString(),
        randomString()
    )

    fun makeOperation() = Operation(randomString(), randomString())

    fun makeLocations(number: Int = 3) = Locations(
        mutableListOf<Location>().apply {
            for (i in 1..number) this.add(makeLocation())
        }
    )

    fun makeOperations(number: Int = 3) = mutableListOf<Operation>().apply {
        for (i in 1..number) this.add(makeOperation())
    }

}