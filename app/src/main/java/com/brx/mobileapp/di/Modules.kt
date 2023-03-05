package com.brx.mobileapp.di

import com.brx.mobileapp.repository.TMDbRepository
import com.brx.mobileapp.repository.datasource.Cache
import com.brx.mobileapp.repository.datasource.TmdbApi
import com.brx.mobileapp.ui.detail.DetailViewModel
import com.brx.mobileapp.ui.main.MainViewModel
import com.brx.mobileapp.usecase.GetDetails
import com.brx.mobileapp.usecase.GetUpcomingMovies
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object Modules {

    val networking = module {
        single { OkHttpClient.Builder().build() }
        single { MoshiConverterFactory.create() as Converter.Factory }
        single { RxJava2CallAdapterFactory.create() as CallAdapter.Factory }
    }

    val dataSources = module {
        single {
            Retrofit.Builder()
                .baseUrl(TmdbApi.BASE_URL)
                .client(get())
                .addConverterFactory(get())
                .addCallAdapterFactory(get())
                .build()
                .create(TmdbApi::class.java)
        }

        single { Cache() }
    }

    val repositories = module {
        single { TMDbRepository(get(), get()) }
    }

    val useCases = module {
        single { GetUpcomingMovies(get()) }
        single { GetDetails(get()) }
    }

    val viewModels = module {
        viewModel { MainViewModel(get()) }
        viewModel { DetailViewModel(get()) }
    }

}