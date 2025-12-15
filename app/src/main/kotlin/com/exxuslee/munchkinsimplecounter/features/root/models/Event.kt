package com.exxuslee.munchkinsimplecounter.features.root.models

sealed class Event {
    data class MainRoute(val route: String) : Event()



}