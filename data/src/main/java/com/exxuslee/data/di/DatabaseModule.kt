package com.exxuslee.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.exxuslee.data.localDB.AppDatabase
import com.exxuslee.data.localPreference.BooleanStorage
import com.exxuslee.data.localPreference.CacheDataSource
import com.exxuslee.data.localPreference.SharedPref
import com.exxuslee.data.utils.Constants.DB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomDatabaseModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DB)
            .fallbackToDestructiveMigration().build()
    }
    factory<CacheDataSource> { CacheDataSource.Base(get()) }
    factory<BooleanStorage> { BooleanStorage.Base(SharedPref.Base().make(androidContext())) }
    factory { get<AppDatabase>().dao }
}