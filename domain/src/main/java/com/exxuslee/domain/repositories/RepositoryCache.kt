package com.exxuslee.domain.repositories

interface RepositoryCache {
    fun loadBoolean(name: String): Boolean
    fun saveBoolean(name: String, data:Boolean)
}