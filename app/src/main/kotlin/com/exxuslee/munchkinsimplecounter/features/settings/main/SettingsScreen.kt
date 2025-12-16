package com.exxuslee.munchkinsimplecounter.features.settings.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.Action
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.Event
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.Event.*
import com.exxuslee.munchkinsimplecounter.ui.common.HSpacer
import com.exxuslee.munchkinsimplecounter.ui.common.Icons
import com.exxuslee.munchkinsimplecounter.ui.common.LocalNavController
import com.exxuslee.munchkinsimplecounter.ui.common.VSpacer
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val navController = LocalNavController.current

    SettingsView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {

        Action.NewGame -> AlertDialog(
            onDismissRequest = { viewModel.clearAction() },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.outline_info_24),
                        contentDescription = stringResource(id = R.string.add_player),
                    )
                    HSpacer(8.dp)
                    Text(text = stringResource(id = R.string.new_game_title))
                }
            },
            text = {
                Text(text = stringResource(id = R.string.new_game_message))
            },
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

        Action.AddPlayer -> {
            var selectedIcon by remember { mutableIntStateOf(0) }
            var name by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { viewModel.clearAction() },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(R.drawable.outline_person_add_24),
                            contentDescription = stringResource(id = R.string.add_player),
                        )
                        HSpacer(8.dp)
                        Text(text = stringResource(id = R.string.add_player))
                    }
                },
                text = {
                    Column {
                        Text(
                            text = stringResource(id = R.string.add_player_message),
                        )
                        VSpacer(16.dp)

                        var expanded by remember { mutableStateOf(false) }

                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text(stringResource(R.string.name)) },
                            leadingIcon = {
                                Box {
                                    Image(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable(
                                                onClick = { expanded = true }
                                            ),
                                        painter = painterResource(id = Icons.icon(selectedIcon)),
                                        contentDescription = stringResource(R.string.select_icon)
                                    )
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        Icons.list.forEachIndexed { index, icon ->
                                            DropdownMenuItem(
                                                text = {
                                                    Image(
                                                        painter = painterResource(id = icon),
                                                        modifier = Modifier.size(36.dp),
                                                        contentDescription = stringResource(R.string.select_icon)
                                                    )
                                                },
                                                onClick = {
                                                    selectedIcon = index
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            },
                            placeholder = { Text(stringResource(R.string.input_name)) },
                            singleLine = true,
                        )
                    }

                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.obtainEvent(
                                AddPlayer(name = name, icon = selectedIcon)
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.add),
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
        }

        Action.PopBack -> {
            viewModel.clearAction()
            navController.popBackStack()
        }
        null -> {}
    }
}