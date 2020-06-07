package com.brx.mobileapp.datasource.remote.repository

import com.brx.mobileapp.datasource.remote.LocationApi
import com.brx.mobileapp.datasource.remote.factory.LocationFactory.makeLocation
import com.brx.mobileapp.datasource.remote.factory.LocationFactory.makeLocations
import com.brx.mobileapp.datasource.remote.factory.PrimitiveFactory.randomInt
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocationRepositoryTest {

    private lateinit var api: LocationApi
    private lateinit var repository: LocationRepository

    @Before
    fun setUp() {
        api = mockk()
        repository = LocationRepository(api)

        // specify which thread to use for IO and Main schedulers
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getLocations_withRemoteData_shouldReturnLocations() {
        every { api.getLocations() } returns Observable.just(makeLocations())

        repository.getLocations()
            .test()
            .assertValue {
                it.listLocations.isNotEmpty()
            }
    }

    @Test
    fun getLocations_withNoData_shouldCompleteWithoutErrors() {
        every { api.getLocations() } returns Observable.empty()

        repository.getLocations()
            .test()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun getLocations_withError_shouldReturnException() {
        every { api.getLocations() } returns Observable.error(Throwable())

        repository.getLocations()
            .test()
            .assertError(Throwable::class.java)
    }

    @Test
    fun getLocation_withRemoteData_shouldReturnLocation() {
        every { api.getLocation(any()) } returns Observable.just(makeLocation())

        repository.getLocation(randomInt())
            .test()
            .assertValue { true }
    }

    @Test
    fun getLocation_withNoData_shouldCompleteWithoutErrors() {
        every { api.getLocation(any()) } returns Observable.empty()

        repository.getLocation(randomInt())
            .test()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun getLocation_withError_shouldReturnException() {
        every { api.getLocation(any()) } returns Observable.error(Throwable())

        repository.getLocation(randomInt())
            .test()
            .assertError(Throwable::class.java)
    }
}