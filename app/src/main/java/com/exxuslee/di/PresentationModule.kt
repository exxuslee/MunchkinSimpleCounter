package com.exxuslee.di

import com.exxuslee.ui.main.MainFragmentViewModel
import com.exxuslee.ui.main.MyPreferences
import com.exxuslee.ui.setting.SettingFragmentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainFragmentViewModel(get()) }
    viewModel { SettingFragmentViewModel(get()) }
    single { MyPreferences(androidApplication()) }
}