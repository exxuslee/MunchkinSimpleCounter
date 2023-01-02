package com.exxuslee.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.exxuslee.data.localDB.Database
import com.exxuslee.data.localPreference.BooleanStorage
import com.exxuslee.data.localPreference.CacheDataSource
import com.exxuslee.data.repositories.RepositoryCacheImpl
import com.exxuslee.data.utils.Constants.DB
import com.exxuslee.domain.repositories.RepositoryCache
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), Database::class.java, DB)
            .fallbackToDestructiveMigration().build()
    }
    factory<CacheDataSource> { CacheDataSource.Base(get()) }
    factory<BooleanStorage> { BooleanStorage.Base(provideSettingsPreferences(androidContext())) }

    factory { get<Database>().dao }
}

private const val PREFERENCES_FILE_KEY = "com.example.settings_preferences"
private fun provideSettingsPreferences(app: Context): SharedPreferences =
    app.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)