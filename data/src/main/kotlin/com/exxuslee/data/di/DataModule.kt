package com.exxuslee.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.exxuslee.data.local.AppDatabase
import com.exxuslee.data.repositories.PlayersRepositoryImpl
import com.exxuslee.data.repositories.PriceRepositoryImpl
import com.exxuslee.data.repositories.SettingsRepositoryImpl
import com.exxuslee.domain.repositories.PlayersRepository
import com.exxuslee.domain.repositories.PriceRepository
import com.exxuslee.domain.repositories.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "DB")
            .fallbackToDestructiveMigration(false).build()
    }

    factory { get<AppDatabase>().playerDAO }
    factory { get<AppDatabase>().priceDAO }

    single<SettingsRepository> { SettingsRepositoryImpl(get()) }

    factory<PlayersRepository> { PlayersRepositoryImpl(get()) }
    factory<PriceRepository> { PriceRepositoryImpl(get()) }
}