package com.exxuslee.munchkinsimplecounter.features.game

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.exxuslee.munchkinsimplecounter.features.settings.main.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    Text("qwe")

    when (viewAction) {

        null -> {}
    }
}