package com.exxuslee.domain.usecases

import com.exxuslee.domain.repositories.RepositoryCache

interface UseCaseCache {
    fun loadBoolean(name: String): Boolean
    fun saveBoolean(name: String, data: Boolean)

    class Base(private val repository: RepositoryCache) : UseCaseCache {
        override fun loadBoolean(name: String) = repository.loadBoolean(name)

        override fun saveBoolean(name: String, data: Boolean) {
            repository.saveBoolean(name, data)
        }

    }
}