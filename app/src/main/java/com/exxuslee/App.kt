package com.exxuslee

import android.app.Application


class App : Application() {
    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        startKoin {
//            androidContext(this@App)
//            if (BuildConfig.DEBUG) androidLogger(Level.ERROR)
//            modules(appModules + domainModules + dataModules)
//            koin.loadModules(appModules + domainModules + dataModules)
//            koin.createRootScope()
 //       }
    }
}

//val appModules = listOf(presentationModule)
//val domainModules = listOf(interactionModule)
//val dataModules = listOf(networkingModule, repositoryModule, databaseModule)