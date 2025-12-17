package com.exxuslee.munchkinsimplecounter.features.game.models

sealed class Action {
    data object ShowSelectPlayerMessage : Action()
    data object AddPlayer : Action()
}