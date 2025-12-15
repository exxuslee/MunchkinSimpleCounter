package com.exxuslee.munchkinsimplecounter.di


import com.exxuslee.munchkinsimplecounter.ui.settings.donate.DonateViewModel
import com.exxuslee.munchkinsimplecounter.ui.settings.language.LanguageViewModel
import com.exxuslee.munchkinsimplecounter.ui.settings.terms.TermsViewModel
import com.exxuslee.munchkinsimplecounter.ui.root.MainViewModel
import com.exxuslee.munchkinsimplecounter.ui.settings.main.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel() }

    viewModel { SettingsViewModel(get(), get()) }
    viewModel { DonateViewModel() }
    viewModel { LanguageViewModel() }
    viewModel { TermsViewModel(get()) }
}