package com.brx.mobileapp.di

import com.brx.mobileapp.datasource.remote.CustomSearchEngineApi
import com.brx.mobileapp.datasource.remote.LocationApi
import com.brx.mobileapp.datasource.remote.repository.ImageRepository
import com.brx.mobileapp.datasource.remote.repository.LocationRepository
import com.brx.mobileapp.ui.main.MainViewModel
import com.brx.mobileapp.usecase.GetLocations
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object Modules {

    val remotes = module {
        single { OkHttpClient.Builder().build() }
        single { MoshiConverterFactory.create() as Converter.Factory }
        single { RxJava2CallAdapterFactory.create() as CallAdapter.Factory }

        single {
            Retrofit.Builder()
                .baseUrl(LocationApi.BASE_URL)
                .client(get())
                .addConverterFactory(get())
                .addCallAdapterFactory(get())
                .build()
                .create(LocationApi::class.java)
        }

        single {
            Retrofit.Builder()
                .baseUrl(CustomSearchEngineApi.BASE_URL)
                .client(get())
                .addConverterFactory(get())
                .addCallAdapterFactory(get())
                .build()
                .create(CustomSearchEngineApi::class.java)
        }
    }

    val repositories = module {
        single { ImageRepository(get()) }
        single { LocationRepository(get()) }
    }

    val useCases = module {
        single { GetLocations(get(), get()) }
    }

    val viewModels = module {
        viewModel { MainViewModel(get()) }
    }

}