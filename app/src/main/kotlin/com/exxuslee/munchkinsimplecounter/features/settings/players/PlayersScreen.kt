package com.exxuslee.munchkinsimplecounter.features.settings.players

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayersScreen(
    viewModel: PlayersViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    LaunchedEffect(Unit) {
        if (viewState.items.isEmpty()) viewModel.sync()
    }

    PlayersView(viewState) {
        viewModel.obtainEvent(it)
    }

}