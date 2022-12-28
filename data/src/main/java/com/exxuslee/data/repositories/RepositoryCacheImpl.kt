package com.exxuslee.data.repositories

import com.exxuslee.data.localPreference.CacheDataSource
import com.exxuslee.domain.repositories.RepositoryCache

class RepositoryCacheImpl(private val cacheDataSource: CacheDataSource) : RepositoryCache {
    override fun loadBoolean(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun saveBoolean(name: String, data: Boolean) {
        TODO("Not yet implemented")
    }
}