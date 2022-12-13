package com.exxuslee.domain.model

data class Player(
    val name: String = "Player",
    val level: Int = 1,
    val bonus: Int = 0,
    val sex: Boolean = Sex.MAN,
    val playing: Boolean = false,
    val reverseSex: Boolean = false,
)