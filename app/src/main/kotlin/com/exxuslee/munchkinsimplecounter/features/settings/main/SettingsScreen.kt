package com.exxuslee.munchkinsimplecounter.features.settings.main

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.Action
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.Event
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

        Action.NewGame -> AlertDialog(
            onDismissRequest = { viewModel.clearAction() },
            title = { Text(text = stringResource(id = R.string.new_game_title)) },
            text = { Text(text = stringResource(id = R.string.new_game_message)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.obtainEvent(Event.ConfirmNewGame)
                }) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.clearAction() }) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        )

        Action.AddPlayer -> AlertDialog(
            onDismissRequest = { viewModel.clearAction() },
            title = { Text(text = stringResource(id = R.string.add_player)) },
            text = {
                Text(text = stringResource(id = R.string.new_game_message))
                   },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.obtainEvent(Event.ConfirmNewGame)
                }) {
                    Text(
                        text = stringResource(id = R.string.add_player),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.clearAction() }) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        )

        null -> {}
    }
}