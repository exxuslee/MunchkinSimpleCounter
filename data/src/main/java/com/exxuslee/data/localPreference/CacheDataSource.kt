package com.exxuslee.data.localPreference

interface CacheDataSource {

    fun read(key: String): Boolean
    fun save(key: String, data: Boolean)

    class Base(private val booleanStorage: BooleanStorage) : CacheDataSource {

        override fun read(key: String) = booleanStorage.read(key, false)

        override fun save(key: String, data: Boolean) {
            booleanStorage.save(key, data)
        }
    }
}