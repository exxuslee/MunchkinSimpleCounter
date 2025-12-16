package com.exxuslee.munchkinsimplecounter.features.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.exxuslee.munchkinsimplecounter.features.game.models.Action
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    GameView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {

        Action.ShowSelectPlayerMessage -> {}
        null -> {}
    }
}