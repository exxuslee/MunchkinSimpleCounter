package com.exxuslee.di

import com.exxuslee.ui.PlayerMapper
import com.exxuslee.ui.main.MainCommunication
import com.exxuslee.ui.main.MainFragmentViewModel
import com.exxuslee.ui.setting.SettingFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainFragmentViewModel(get(), get(), get(), get()) }
    viewModel { SettingFragmentViewModel(get(), get(), get()) }
    factory<MainCommunication.Mutable> { MainCommunication.Base() }
    factory<PlayerMapper.Base> { PlayerMapper.Base() }
}