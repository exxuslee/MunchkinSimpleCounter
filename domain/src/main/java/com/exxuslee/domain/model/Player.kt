package com.exxuslee.domain.model

data class Player(
    val id: Int,
    val name: String = "Player",
    val level: Int = 1,
    val bonus: Int = 0,
    val sex: Boolean = Sex.MAN,
    val active: Boolean = false
)