package com.exxuslee.domain.utils

interface HandleResult<T> {
    fun handleSuccess (data: T)
    fun handleError (message: String)
}