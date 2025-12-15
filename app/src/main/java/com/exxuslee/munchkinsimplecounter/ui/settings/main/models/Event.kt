package com.exxuslee.munchkinsimplecounter.ui.settings.main.models

sealed class Event {
    class IsDark(val newValue: Boolean) : Event()

}