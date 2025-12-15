package com.exxuslee.munchkinsimplecounter.ui.settings.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    SettingsView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {

        null -> {}
    }
}