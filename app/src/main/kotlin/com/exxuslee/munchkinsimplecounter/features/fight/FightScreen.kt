package com.exxuslee.munchkinsimplecounter.features.fight

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun FightScreen(
    viewModel: FightViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    FightView(viewState) {
        viewModel.obtainEvent(it)
    }
}