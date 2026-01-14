package com.exxuslee.munchkinsimplecounter.features.fight.models

/**
 * События от UI для экрана боя.
 * ViewModel обрабатывает эти события и обновляет [ViewState].
 */
sealed class Event {

    // Управление уровнем/силой героя
    data class ChangeHeroLevel(val delta: Int) : Event()
    data class ChangeHelperLevel(val delta: Int) : Event()

    // Работа с помощником
    object AddHelper : Event()
    object RemoveHelper : Event()

    // Работа с монстрами
    object AddMonster : Event()
    data class RemoveMonster(val monsterId: Int) : Event()

    // Модификаторы
    data class AddHeroModifier(val value: Int) : Event()
    data class AddHelperModifier(val value: Int) : Event()
    data class AddMonsterModifier(val monsterId: Int, val value: Int) : Event()

    data class RemoveModifier(val modifierId: Long) : Event()
}
