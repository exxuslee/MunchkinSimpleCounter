package com.exxuslee.ui.main

import com.exxuslee.domain.model.Player

interface PlayerMapper {
    fun map(oldPlayer: Player, level: Int?, bonus: Int?, reverseSex: Boolean?): Player

    class Base : PlayerMapper {
        override fun map(oldPlayer: Player, level: Int?, bonus: Int?, reverseSex: Boolean?) =
            Player(
                id = oldPlayer.id,
                name = oldPlayer.name,
                level = level ?: oldPlayer.level,
                bonus = bonus ?: oldPlayer.bonus,
                icon = oldPlayer.icon,
                playing = true,
                reverseSex = reverseSex?: oldPlayer.reverseSex
            )
    }
}
