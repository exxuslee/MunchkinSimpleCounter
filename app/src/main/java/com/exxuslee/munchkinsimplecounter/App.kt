package com.exxuslee.munchkinsimplecounter

import android.app.Application
import com.exxuslee.data.di.roomDatabaseModule
import com.exxuslee.data.di.repositoryModule
import com.exxuslee.munchkinsimplecounter.di.presentationModule
import com.exxuslee.domain.di.interactionModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            //if (BuildConfig.DEBUG) androidLogger(Level.ERROR)
            //modules(appModules + domainModules + dataModules)
            koin.loadModules(com.exxuslee.munchkinsimplecounter.appModules + com.exxuslee.munchkinsimplecounter.domainModules + com.exxuslee.munchkinsimplecounter.dataModules)
        }
    }
}

val appModules = listOf(presentationModule)
val domainModules = listOf(interactionModule)
val dataModules = listOf(repositoryModule, roomDatabaseModule)