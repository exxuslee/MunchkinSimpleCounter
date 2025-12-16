package com.exxuslee.munchkinsimplecounter.features.game.models

import com.exxuslee.domain.model.Player

sealed class Event {
    data class SelectPlayer(val id: Int) : Event()
    data class SwitchSex(val player: Player) : Event()
    data object AddLevel : Event()
    data object SubLevel : Event()
    data object AddBonus : Event()
    data object SubBonus : Event()
}