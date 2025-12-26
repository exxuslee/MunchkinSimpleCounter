package com.exxuslee.munchkinsimplecounter.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exxuslee.munchkinsimplecounter.features.game.models.BottomButtonsItems
import com.exxuslee.munchkinsimplecounter.features.game.models.Event
import com.exxuslee.munchkinsimplecounter.managers.ClickSound
import com.exxuslee.munchkinsimplecounter.managers.rememberSoundManager

@Composable
fun BottomNavigationBar(
    eventHandler: (Event) -> Unit,
) {
    val soundManager = rememberSoundManager()

    NavigationBar(
        modifier = Modifier.height(56.dp),
        windowInsets = WindowInsets(),
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
                    eventHandler.invoke(
                        when (dest) {
                            BottomButtonsItems.AddLevel -> {
                                soundManager.play(ClickSound.ADD_LEVEL)
                                Event.AddLevel
                            }

                            BottomButtonsItems.SubLevel -> {
                                soundManager.play(ClickSound.SUB_LEVEL)
                                Event.SubLevel
                            }

                            BottomButtonsItems.AddBonus -> {
                                soundManager.play(ClickSound.ADD_BONUS)
                                Event.AddBonus
                            }

                            BottomButtonsItems.SubBonus -> {
                                soundManager.play(ClickSound.SUB_BONUS)
                                Event.SubBonus
                            }
                        }
                    )
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