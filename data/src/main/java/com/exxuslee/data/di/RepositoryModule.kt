package com.exxuslee.data.di

import com.exxuslee.data.repositories.RepositoryCacheImpl
import com.exxuslee.data.repositories.RepositoryImpl
import com.exxuslee.data.repositories.SettingsRepositoryImpl
import com.exxuslee.domain.repositories.RepositoryCache
import com.exxuslee.domain.repositories.RepositoryDB
import com.exxuslee.domain.repositories.SettingsRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }

    factory<RepositoryDB> { RepositoryImpl(get()) }
    factory<RepositoryCache> { RepositoryCacheImpl(get()) }
}