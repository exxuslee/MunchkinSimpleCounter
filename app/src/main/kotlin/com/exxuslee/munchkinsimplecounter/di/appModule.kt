package com.exxuslee.munchkinsimplecounter.di

import com.exxuslee.data.di.dataModule
import com.exxuslee.domain.di.domainModule
import org.koin.dsl.module

val appModule = module {
    includes(presentationModule)
    includes(domainModule)
    includes(dataModule)
}