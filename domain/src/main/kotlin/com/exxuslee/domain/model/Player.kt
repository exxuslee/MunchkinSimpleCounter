package com.exxuslee.domain.model

data class Player(
    override val id: Int = 0,
    override val level: Int = 1,
    val name: String,
    val bonus: Int = 0,
    val icon: Int = 0,
    val playing: Boolean = true,
    val reverseSex: Boolean = false,
    val startSex: Boolean = false,
) : GameUnit(
    id = id,
    level = level + bonus,
)
