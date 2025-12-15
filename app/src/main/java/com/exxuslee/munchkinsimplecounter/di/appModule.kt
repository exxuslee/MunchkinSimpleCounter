package com.exxuslee.munchkinsimplecounter.di

import com.exxuslee.data.di.repositoryModule
import com.exxuslee.data.di.roomDatabaseModule
import com.exxuslee.domain.di.interactionModule
import org.koin.dsl.module

val appModule = module {
    includes(presentationModule)
    includes(interactionModule)
    includes(repositoryModule)
    includes(roomDatabaseModule)
}