package com.exxuslee.munchkinsimplecounter.features.settings.main.models

import com.exxuslee.domain.model.Player

data class ViewState(
    val isDark: Boolean = false,
    val isSound: Boolean = false,
    val isTermsOfUseRead: Boolean = false,
    val players: List<Player> = emptyList(),
    val revealedId: Int = -1,
)