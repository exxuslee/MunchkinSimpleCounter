package com.exxuslee.munchkinsimplecounter.features.settings.players.models

import com.exxuslee.domain.model.Player


data class ViewState(
    val items: List<Player> = emptyList(),
    val revealCardId: Long = -1,
)