package com.exxuslee

import android.app.Application
import com.exxuslee.data.di.databaseModule
import com.exxuslee.data.di.repositoryModule
import com.exxuslee.di.presentationModule
import com.exxuslee.domain.di.interactionModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            if (BuildConfig.DEBUG) androidLogger(Level.ERROR)
            //modules(appModules + domainModules + dataModules)
            koin.loadModules(appModules + domainModules + dataModules)
            koin.createRootScope()
        }
    }
}

val appModules = listOf(presentationModule)
val domainModules = listOf(interactionModule)
val dataModules = listOf(repositoryModule, databaseModule)