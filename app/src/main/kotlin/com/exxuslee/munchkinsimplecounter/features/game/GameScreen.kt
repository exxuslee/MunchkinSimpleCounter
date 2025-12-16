package com.exxuslee.munchkinsimplecounter.features.game

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.exxuslee.munchkinsimplecounter.R
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

        Action.ShowSelectPlayerMessage -> {
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.select_player_toast_message),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.clearAction()
        }

        null -> {}
    }
}