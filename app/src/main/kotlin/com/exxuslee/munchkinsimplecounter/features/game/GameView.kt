package com.exxuslee.munchkinsimplecounter.features.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.exxuslee.munchkinsimplecounter.features.game.models.Event
import com.exxuslee.munchkinsimplecounter.features.game.models.ViewState

@Composable
fun GameView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    Column {
        PlayerCard(null)
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(viewState.players) { player ->
                PlayerCard(
                    player.icon,
                    name = player.name,
                    level = player.level.toString(),
                    bonus = player.bonus.toString(),
                    life = (player.level + player.bonus).toString()
                )
            }
        }
    }

}