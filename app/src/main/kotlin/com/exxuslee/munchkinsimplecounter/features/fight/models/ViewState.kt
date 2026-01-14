package com.exxuslee.munchkinsimplecounter.features.fight.models

data class ViewState(
    val heroes: List<UnitItem> = emptyList(),
    val monsters: List<UnitItem> = emptyList(),
)
