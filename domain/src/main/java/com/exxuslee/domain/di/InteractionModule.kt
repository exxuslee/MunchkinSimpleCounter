package com.exxuslee.domain.di

import com.exxuslee.domain.usecases.SettingsUseCase
import com.exxuslee.domain.usecases.ThemeController
import com.exxuslee.domain.usecases.UseCaseCache
import com.exxuslee.domain.usecases.UseCaseDB
import org.koin.dsl.module

val interactionModule = module {
    single<ThemeController> { ThemeController.Base(get()) }
    single<SettingsUseCase> { SettingsUseCase.Base(get()) }

    factory<UseCaseDB.Base> { UseCaseDB.Base(get()) }
    factory<UseCaseCache.Base> { UseCaseCache.Base(get()) }
}