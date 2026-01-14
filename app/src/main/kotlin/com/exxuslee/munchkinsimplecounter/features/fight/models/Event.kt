package com.exxuslee.munchkinsimplecounter.features.fight.models

sealed class Event {

    data class AddHero(val heroId: Int) : Event()
    data class RemoveHero(val heroId: Int) : Event()

    object AddMonster : Event()
    data class RemoveMonster(val monsterId: Int) : Event()

    data class AddModifier(val id: Int, val value: Int) : Event()
    data class RemoveModifier(val id: Int) : Event()

}
