package com.exxuslee.munchkinsimplecounter.features.root.models

sealed class MainEvent {
    data class MainRoute(val route: String) : MainEvent()
    data object Dice : MainEvent()

}