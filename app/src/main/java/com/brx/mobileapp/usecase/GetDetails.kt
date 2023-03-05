package com.brx.mobileapp.usecase

import com.brx.mobileapp.repository.TMDbRepository
import com.brx.mobileapp.repository.response.MovieResponse
import com.brx.mobileapp.usecase.model.MovieModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetDetails(
    private val location: TMDbRepository
) : IUseCase<GetDetails.Param, MovieModel> {

    override fun execute(param: Param): Single<MovieModel> = location.getDetails(param.id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map {
            it.toMovieModel()
        }
        .firstOrError()

    data class Param(val id: Long)
} 