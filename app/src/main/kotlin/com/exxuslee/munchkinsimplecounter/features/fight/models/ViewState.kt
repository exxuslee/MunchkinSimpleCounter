package com.exxuslee.munchkinsimplecounter.features.fight.models

import com.exxuslee.domain.model.GameUnit

data class ViewState(
    val heroes: List<UnitItem> = emptyList(),
    val monsters: List<UnitItem> = listOf(
        UnitItem(
            unit = GameUnit(
                id = 1,
                level = 1
            ),
            spells = emptyList(),
        )
    ),
)
