package com.exxuslee.munchkinsimplecounter.features.game.models

import com.exxuslee.domain.model.Player
import com.exxuslee.domain.model.UiState

data class ViewState(
    val isDark: Boolean = false,
    val activePlayers: List<Player> = emptyList(),
    val allPlayers: Int = 0,
    val selectedPlayerId: Int? = null,
    val state: UiState = UiState.Loading,
)