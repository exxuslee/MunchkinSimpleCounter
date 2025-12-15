package com.exxuslee.munchkinsimplecounter.ui.root.models

sealed class Event {
    data class MainRoute(val route: String) : Event()



}