package com.exxuslee.domain.di

import com.exxuslee.domain.usecases.PlayersUseCase
import com.exxuslee.domain.usecases.PriceUseCase
import com.exxuslee.domain.usecases.SettingsUseCase
import com.exxuslee.domain.usecases.ThemeController
import org.koin.dsl.module

val domainModule = module {
    single<ThemeController> { ThemeController.Base(get()) }
    single<SettingsUseCase> { SettingsUseCase.Base(get()) }
    single<PlayersUseCase> { PlayersUseCase.Base(get()) }
    single<PriceUseCase> { PriceUseCase.Base(get()) }

}