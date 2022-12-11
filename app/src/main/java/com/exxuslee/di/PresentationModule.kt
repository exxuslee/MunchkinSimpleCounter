package com.exxuslee.di

import com.exxuslee.ui.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainFragmentViewModel(get()) }
}