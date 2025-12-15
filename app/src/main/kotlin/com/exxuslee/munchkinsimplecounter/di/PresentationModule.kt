package com.exxuslee.munchkinsimplecounter.di



import com.exxuslee.munchkinsimplecounter.features.game.GameViewModel
import com.exxuslee.munchkinsimplecounter.features.root.MainViewModel
import com.exxuslee.munchkinsimplecounter.features.settings.donate.DonateViewModel
import com.exxuslee.munchkinsimplecounter.features.settings.language.LanguageViewModel
import com.exxuslee.munchkinsimplecounter.features.settings.main.SettingsViewModel
import com.exxuslee.munchkinsimplecounter.features.settings.terms.TermsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel() }

    viewModel { GameViewModel() }

    viewModel { SettingsViewModel(get(), get(), get()) }
    viewModel { DonateViewModel() }
    viewModel { LanguageViewModel() }
    viewModel { TermsViewModel(get()) }
}