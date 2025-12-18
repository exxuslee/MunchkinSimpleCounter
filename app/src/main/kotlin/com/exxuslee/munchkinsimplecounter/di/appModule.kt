package com.exxuslee.munchkinsimplecounter.di

import com.exxuslee.data.di.dataModule
import com.exxuslee.domain.di.domainModule
import com.exxuslee.munchkinsimplecounter.puzzle.PuzzleWorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    includes(presentationModule)
    includes(domainModule)
    includes(dataModule)

    single { PuzzleWorkManager(androidContext()) }

}