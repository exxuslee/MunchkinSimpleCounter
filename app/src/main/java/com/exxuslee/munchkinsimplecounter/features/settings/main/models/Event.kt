package com.exxuslee.munchkinsimplecounter.features.settings.main.models

sealed class Event {
    class IsDark(val newValue: Boolean) : Event()
    object ConfirmNewGame : Event()
    object IsNewGame : Event()


}