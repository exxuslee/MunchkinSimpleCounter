package com.exxuslee.munchkinsimplecounter.features.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.game.models.Event
import com.exxuslee.munchkinsimplecounter.features.game.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.ListEmptyView

@Composable
fun GameView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    Column {
        if (viewState.players.isEmpty()) ListEmptyView(
            text = stringResource(R.string.game_empty_players_list),
            icon = R.drawable.outline_empty_dashboard_24
        )
        PlayerCard(null)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(viewState.players) { player ->
                val icon = if (player.reverseSex) player.icon + 1 else player.icon
                PlayerCard(
                    icon,
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