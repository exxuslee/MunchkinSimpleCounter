package com.exxuslee.munchkinsimplecounter.features.settings.main.models

sealed class Event {
    data class IsDark(val newValue: Boolean) : Event()
    object ConfirmNewGame : Event()
    object DialogNewGame : Event()
    object DialogAddPlayer : Event()
    data class AddPlayer(val name: String, val icon: Int) : Event()


}