package com.exxuslee.data.di

import androidx.room.Room
import com.exxuslee.data.localDB.Database
import com.exxuslee.data.localPreference.BooleanStorage
import com.exxuslee.data.localPreference.CacheDataSource
import com.exxuslee.data.localPreference.SharedPref
import com.exxuslee.data.utils.Constants.DB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), Database::class.java, DB)
            .fallbackToDestructiveMigration().build()
    }
    factory<CacheDataSource> { CacheDataSource.Base(get()) }
    factory<BooleanStorage> { BooleanStorage.Base(SharedPref.Base().make(androidContext())) }
    factory { get<Database>().dao }
}