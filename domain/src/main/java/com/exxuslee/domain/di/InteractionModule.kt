package com.exxuslee.domain.di

import com.exxuslee.domain.usecases.UseCaseCache
import com.exxuslee.domain.usecases.UseCaseDB
import org.koin.dsl.module

val interactionModule = module {
    factory<UseCaseDB.Base> { UseCaseDB.Base(get()) }
    factory<UseCaseCache.Base> { UseCaseCache.Base(get()) }
}