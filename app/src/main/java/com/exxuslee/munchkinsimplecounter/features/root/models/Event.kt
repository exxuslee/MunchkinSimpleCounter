package com.exxuslee.munchkinsimplecounter.features.game.root.models

sealed class Event {
    data class MainRoute(val route: String) : Event()



}