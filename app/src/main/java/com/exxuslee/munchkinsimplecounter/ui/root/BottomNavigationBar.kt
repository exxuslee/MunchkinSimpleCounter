package com.exxuslee.munchkinsimplecounter.ui.root

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exxuslee.munchkinsimplecounter.ui.root.models.BottomButtonsItems
import com.exxuslee.munchkinsimplecounter.ui.root.models.Event
import com.exxuslee.munchkinsimplecounter.ui.root.models.ViewState

@Composable
fun BottomNavigationBar(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
    ) {
        listOf(
            BottomButtonsItems.AddLevel,
            BottomButtonsItems.SubLevel,
            BottomButtonsItems.AddBonus,
            BottomButtonsItems.SubBonus,
        ).forEach { dest ->
            NavigationBarItem(
                selected = false,
                onClick = {

                },
                icon = {
                    Icon(
                        painter = dest.icon(),
                        contentDescription = dest.label(),
                        modifier = Modifier.size(30.dp)
                    )
                },
            )
        }
    }
}