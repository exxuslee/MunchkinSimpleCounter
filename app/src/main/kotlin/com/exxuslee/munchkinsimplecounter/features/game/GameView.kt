package com.exxuslee.munchkinsimplecounter.features.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exxuslee.munchkinsimplecounter.features.game.models.Event
import com.exxuslee.munchkinsimplecounter.features.game.models.ViewState
import com.exxuslee.munchkinsimplecounter.features.root.BottomNavigationBar

@Composable
fun GameView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    Column {
        PlayerCard(null)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(viewState.players) { player ->
                PlayerCard(
                    player.icon,
                    name = player.name,
                    level = player.level.toString(),
                    bonus = player.bonus.toString(),
                    life = (player.level + player.bonus).toString(),
                    selected = player.id == viewState.selectedPlayerId,
                    onSelectRow = {
                        eventHandler.invoke(Event.SelectPlayer(player.id))
                    },
                    onSelectIcon = {
                        eventHandler.invoke(Event.SwitchSex(player))
                    },
                )
            }
        }
        BottomNavigationBar { event ->
            eventHandler(event)
        }
    }

}