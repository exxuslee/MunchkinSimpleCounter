package com.exxuslee.munchkinsimplecounter.features.fight.models

import com.exxuslee.domain.model.GameUnit
import com.exxuslee.domain.model.Player

data class ViewState(
    val heroes: List<UnitItem> = emptyList(),
    val monsters: List<UnitItem> = listOf(
        UnitItem(
            unit = GameUnit(
                id = -1,
                attack = 1,
            ),
            spells = emptyList(),
        )
    ),
    val revealedId: Int? = null,
    val activePlayers: List<Player> = emptyList(),
)
