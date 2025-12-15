package com.exxuslee.munchkinsimplecounter.features.settings.players.models

sealed class Event {

    data object Add : Event()
    data class Delete(val id: Long) : Event()
    data class Active(val id: Long) : Event()

}