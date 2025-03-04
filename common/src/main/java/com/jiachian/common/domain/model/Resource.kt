package com.jiachian.common.domain.model

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val t: Throwable?) : Resource<T>()
    class Loading<T> : Resource<T>()
}