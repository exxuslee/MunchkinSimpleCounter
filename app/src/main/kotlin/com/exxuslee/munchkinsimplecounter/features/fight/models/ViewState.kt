package com.exxuslee.munchkinsimplecounter.features.fight.models

import com.exxuslee.domain.model.Player

data class ViewState(
    val hero: Player? = null,
    val helper: Player? = null,
    val monsters: List<MonsterCard> = emptyList(),
    val heroModifiers: List<FightModifier> = emptyList(),
    val helperModifiers: List<FightModifier> = emptyList(),
) {
    val heroPower: Int
        get() = (hero?.level ?: 0) + (hero?.bonus ?: 0) + heroModifiers.sumOf { it.value }

    val helperPower: Int
        get() = (helper?.level ?: 0) + (helper?.bonus ?: 0) + helperModifiers.sumOf { it.value }

    val monstersPower: Int
        get() = monsters.sumOf { it.totalPower }
}

/**
 * Карточка монстра в бою.
 */
data class MonsterCard(
    val id: Int,
    val name: String,
    val basePower: Int,
    val modifiers: List<FightModifier> = emptyList(),
) {
    val totalPower: Int
        get() = basePower + modifiers.sumOf { it.value }
}

/**
 * Универсальный модификатор (усиление/ослабление).
 */
data class FightModifier(
    val id: Long,
    val value: Int, // может быть как >0, так и <0
    val label: String? = null,
)
