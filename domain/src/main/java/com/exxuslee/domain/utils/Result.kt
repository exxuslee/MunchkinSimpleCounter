package com.exxuslee.domain.utils

sealed class Result<out R> {

    abstract fun<T> handle(handleResult: HandleResult<T>)

    class Success<out T>(private val value: T) : Result<T>() {
        override fun <T> handle(handleResult: HandleResult<T>) {
            handleResult.handleSuccess(value as T)
        }
    }

    class Error(private val message: String) : Result<Nothing>() {
        override fun <T> handle(handleResult: HandleResult<T>) {
            handleResult.handleError(message)
        }
    }
}