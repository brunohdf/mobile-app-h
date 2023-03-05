package com.brx.mobileapp.usecase

import com.brx.mobileapp.repository.TMDbRepository
import com.brx.mobileapp.repository.response.MovieResponse
import com.brx.mobileapp.usecase.model.MovieModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetUpcomingMovies(
    private val location: TMDbRepository
) : IUseCase<GetUpcomingMovies.Param, MutableList<MovieModel>> {

    override fun execute(param: Param): Single<MutableList<MovieModel>> = location.getUpcomingMovies()
        .subscribeOn(Schedulers.io())
        .flatMap { list -> Observable.fromIterable(list) }
        .observeOn(AndroidSchedulers.mainThread())
        .map { it.toMovieModel() }
        .toList()

    data class Param(val page: Long)
} 