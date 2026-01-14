package com.exxuslee.munchkinsimplecounter.features.fight.models

import com.exxuslee.domain.model.GameUnit

data class UnitItem(
    val unit: GameUnit,
    val spells: List<Int> = emptyList(),
) 