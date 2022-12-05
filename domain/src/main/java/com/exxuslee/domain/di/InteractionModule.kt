package com.exxuslee.domain.di

import com.exxuslee.domain.usecases.UseCase
import org.koin.dsl.module

val interactionModule = module {
    factory<UseCase.Base> { UseCase.Base(get()) }
}