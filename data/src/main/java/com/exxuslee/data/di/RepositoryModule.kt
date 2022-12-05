package com.exxuslee.data.di

import com.exxuslee.data.repositories.RepositoryImpl
import com.exxuslee.domain.repositories.Repository
import org.koin.dsl.module


val repositoryModule = module {
    factory<Repository> { RepositoryImpl(get()) }
}