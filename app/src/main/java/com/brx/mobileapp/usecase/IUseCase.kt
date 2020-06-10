package com.brx.mobileapp.usecase

import io.reactivex.Single

interface IUseCase<P, T> {
    fun execute(param: P): Single<T>
} 