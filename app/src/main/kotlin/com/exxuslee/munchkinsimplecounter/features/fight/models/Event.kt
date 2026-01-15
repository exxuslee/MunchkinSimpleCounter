package com.exxuslee.munchkinsimplecounter.features.fight.models

sealed class Event {

    data class AddHero(val heroId: Int) : Event()
    data class RemoveHero(val heroId: Int) : Event()
    object AddMonster : Event()
    data class RemoveMonster(val monsterId: Int) : Event()
    data class AddModifier(val id: Int, val value: Int) : Event()
    data class ChangeMonsterLevel(val id: Int, val value: Int) : Event()
    data class RemoveModifier(val unitId: Int, val modifierIndex: Int) : Event()
    data class Reveal(val id: Int?) : Event()

}
