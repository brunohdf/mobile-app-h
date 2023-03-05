package com.brx.mobileapp.factory

import com.brx.mobileapp.factory.ResponseFactory.makeMovieResponse
import com.brx.mobileapp.usecase.model.MovieModel
import com.brx.mobileapp.usecase.toMovieModel

object ModelFactory {

    fun makeMovieModelList(number: Int = 3): MutableList<MovieModel> {
        return mutableListOf<MovieModel>().apply {
            for (i in 1..number) this.add(makeMovieResponse().toMovieModel())
        }
    }
}